library IEEE;
use IEEE.STD_logic_1164.ALL;

entity REG_8BITS_BIDIR_SINCRON is
	Port(Si: in bit;
	Dir: in bit;
	--0=stanga, 1=dreapta
	Clock: in bit;
	Registru: out bit_vector (7 downto 0) := "00000000");
end REG_8BITS_BIDIR_SINCRON;

architecture REG_8BITS_BIDIR_SINCRON_ARCH of REG_8BITS_BIDIR_SINCRON is
begin
	process(Clock)
	variable R: bit_vector (7 downto 0) := "00000000";
	begin
		if Clock'event and Clock = '1' then
			if Dir = '0' then
				R := R sll 1;
				R(0) := Si;
			else
				R := R srl 1;
				R(7) := Si;
			end if;
		end if;
		Registru <= R;
	end process;
end REG_8BITS_BIDIR_SINCRON_ARCH;
