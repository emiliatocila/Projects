library IEEE;
use IEEE.STD_logic_1164.ALL;

entity MUX_4TO1 is
	generic(NUMAR_DE_BITI: natural := 2);
	Port (SEL0 : in bit;
	SEL1 : in bit;
	I0 : in bit_vector(NUMAR_DE_BITI downto 0);
	I1 : in bit_vector(NUMAR_DE_BITI downto 0);
	I2 : in bit_vector(NUMAR_DE_BITI downto 0);
	I3 : in bit_vector(NUMAR_DE_BITI downto 0);
	Y : out bit_vector(NUMAR_DE_BITI downto 0));
end MUX_4TO1;

architecture MUX_4TO1_ARCH of MUX_4TO1 is
begin
	Y <= I0 when (SEL0 = '0' and SEL1 = '0') else 
	I1 when (SEL0 = '1' and SEL1 = '0') else
	I2 when (SEL0 = '0' and SEL1 = '1') else I3;
end MUX_4TO1_ARCH;
	