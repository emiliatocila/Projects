library IEEE;
use IEEE.STD_LOGIC_1164.all;
use IEEE.STD_LOGIC_UNSIGNED.all;

entity CLOCK_DIVIDER is
	port (MCLK, CLR: in STD_LOGIC;
	CLK_DIVIZAT, CLK_DIVIZAT_AFIS: out STD_LOGIC);
end CLOCK_DIVIDER;

architecture CLOCK_DIVIDER_ARCH of CLOCK_DIVIDER is

signal Q: STD_LOGIC_VECTOR(27 downto 0);	

begin		 
	
	process(MCLK, CLR)
	begin
		if (CLR = '1' or Q = "U") then
			Q <= X"0000000";
		elsif (MCLK'EVENT and MCLK = '1' and MCLK'LAST_VALUE = '0') then
			Q <= Q + 1;
		end if;
	end process; 
	
	CLK_DIVIZAT <= Q(4); -- 0.5 secunde	= Q(26)	
	CLK_DIVIZAT_AFIS <= Q(1); -- vom avea nevoie de 190 Hz pentru afisor / -- Q(22)	/ --Q(17)
end CLOCK_DIVIDER_ARCH; 