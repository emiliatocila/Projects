library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

entity BCD_7_DECODER is
	Port(inputBCD : in bit_vector(3 downto 0);
	outputBCD : out bit_vector(6 downto 0));
end BCD_7_DECODER;

architecture BCD_7_DECODER_ARCH of BCD_7_DECODER is
begin
	process(inputBCD)
	begin
		case inputBCD is
			when "0000" => outputBCD <= "0000001";
			when "0001" => outputBCD <= "1001111";
			when "0010" => outputBCD <= "0010010";
			when "0011" => outputBCD <= "0000110";
			when "0100" => outputBCD <= "1001100";
			when "0101" => outputBCD <= "0100100";
			when "0110" => outputBCD <= "0001111";
			when "1000" => outputBCD <= "0000000";
			when "1001" => outputBCD <= "0000100";
			when others => outputBCD <= "1111111";
		end case;
	end process;
end BCD_7_DECODER_ARCH;
		