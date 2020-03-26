library IEEE;
use IEEE.STD_LOGIC_1164.all;

entity DEBOUNCE4_PUSH_BUTTONS is
	port (CCLK, CLR: in STD_LOGIC; --clk divizat
	INP: in STD_LOGIC; 
	OUTP: out STD_LOGIC);
end DEBOUNCE4_PUSH_BUTTONS;

architecture DEBOUNCE4_ARCH of DEBOUNCE4_PUSH_BUTTONS is  

signal DELAY1, DELAY2, DELAY3: STD_LOGIC;   -- iesirile bistabilelor D

begin
	
	process(CCLK, CLR)
	begin
		if (CLR = '1') then
			DELAY1 <= '0';
			DELAY2 <= '0';
			DELAY3 <= '0';
		elsif (CCLK'EVENT and CCLK = '1' and CCLK'LAST_VALUE = '0') then 
			DELAY1 <= INP;
			DELAY2 <= DELAY1;
			DELAY3 <= DELAY2;
		end if;
	end process;	
	
	OUTP <= DELAY1 and DELAY2 and DELAY3;  
	
end DEBOUNCE4_ARCH;
