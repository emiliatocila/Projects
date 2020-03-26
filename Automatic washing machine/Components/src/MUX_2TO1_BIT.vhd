library IEEE;
use IEEE.STD_LOGIC_1164.all; 
use IEEE.STD_LOGIC_UNSIGNED.all;

entity MUX_2TO1_BIT is
	port (SEL: in STD_LOGIC;
	A, B: in STD_LOGIC;
	Y: out STD_LOGIC);
end MUX_2TO1_BIT;

architecture MUX_2TO1_BIT_ARCH of MUX_2TO1_BIT is 

begin
	process(SEL, A, B)
	begin
		if (SEL = '0') then
			Y <= A;
		else Y <= B;
		end if;
	end process; 
	
end MUX_2TO1_BIT_ARCH;