library IEEE;
use IEEE.STD_LOGIC_1164.all; 
use IEEE.STD_LOGIC_UNSIGNED.all;

entity MUX_2TO1 is
	generic (N: integer := 2);
	port (SEL: in STD_LOGIC;
	A, B: in STD_LOGIC_VECTOR((N - 1) downto 0);
	Y: out STD_LOGIC_VECTOR((N - 1) downto 0));
end MUX_2TO1;

architecture MUX_2TO1_ARCH of MUX_2TO1 is  

begin
	process(SEL, A, B)
	begin
		if (SEL = '0') then
			Y <= A;
		else 
			Y <= B;
		end if;
	end process;
	
end MUX_2TO1_ARCH;