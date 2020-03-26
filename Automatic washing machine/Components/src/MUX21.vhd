library IEEE;
use IEEE.STD_logic_1164.ALL;

entity MUX_2TO1 is
	Port (SEL: in BIT;
    A: in BIT;
	B: in BIT;
	Y: out BIT);
end MUX_2TO1;

architecture MUX_2TO1_ARCH of MUX_2TO1 is
begin
	Y <= A when (SEL = '0') else B;
end MUX_2TO1_ARCH;