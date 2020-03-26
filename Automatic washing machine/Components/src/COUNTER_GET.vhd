library IEEE;
use IEEE.STD_LOGIC_1164.all;  
use IEEE.STD_LOGIC_UNSIGNED.all;

entity COUNTER_GET is
	generic (limita: INTEGER := 4;
	N : integer := 1);
	port (CLR, CLK: in STD_LOGIC;
	Q: out STD_LOGIC_VECTOR((N - 1) downto 0));
end COUNTER_GET;

architecture COUNTER_GET_ARCH of COUNTER_GET is

signal COUNT: STD_LOGIC_VECTOR((N - 1) downto 0);

begin
	process(CLK, CLR)
	begin
		if (CLR = '1' or COUNT = "U") then
			COUNT <= (others => '0');
		elsif (CLK'EVENT and CLK = '1' and CLK'LAST_VALUE = '0') then 	  
			if (COUNT = limita) then
				COUNT <= (others => '0');
			else COUNT <= COUNT + 1; 
			end if;
		end if;
	end process;
	
	Q <= COUNT;	
	
end COUNTER_GET_ARCH;