library IEEE;				
use IEEE.STD_LOGIC_1164.all;
use IEEE.STD_LOGIC_UNSIGNED.all;
use IEEE.NUMERIC_STD.all;

entity TIMP_AFISOR is
	port(CLK, START, USA: in STD_LOGIC;
	TIMP_IN: in NATURAL;
	TIMP_NATURAL: out NATURAL;
	TIMP: out STD_LOGIC_VECTOR(11 downto 0));		  
end TIMP_AFISOR;

architecture TIMP_AFISOR_ARCH of TIMP_AFISOR is	

signal COUNT, COUNT_DEBLOCARE_USA: NATURAL;

begin	
	process(CLK, START)	
	
	variable OK: BIT := '0'; 
	variable NEXT_CLOCK: BIT := '0';
	
	begin 
	if (CLK'EVENT and CLK = '1' and CLK'LAST_VALUE = '0') then
		if (START = '1' and USA = '1') then
			OK := '1'; 	   
			COUNT_DEBLOCARE_USA <= 6;
		end if;	
		if (OK = '1') then
			if (COUNT /= 0) then 
					if (COUNT = TIMP_IN and NEXT_CLOCK = '0') then
						NEXT_CLOCK := '1';
					elsif (NEXT_CLOCK = '1') then
						COUNT <= COUNT - 1;	
					end if;	
			else 
				OK := '0'; 
				NEXT_CLOCK := '0'; 
			end if;
		else 
			if (OK = '0' and COUNT_DEBLOCARE_USA /= 0) then 
					COUNT_DEBLOCARE_USA <= COUNT_DEBLOCARE_USA - 1;	 
			else 
				COUNT <= TIMP_IN;
			end if;
		end if;
	end if;
	end process; 
	
	TIMP_NATURAL <= COUNT;
	TIMP <= STD_LOGIC_VECTOR(to_unsigned(COUNT, 12));

end TIMP_AFISOR_ARCH;