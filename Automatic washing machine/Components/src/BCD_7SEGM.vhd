library IEEE;
use IEEE.STD_LOGIC_1164.all;
use IEEE.STD_LOGIC_UNSIGNED.all;

entity BCD_7_DECODER is
	port(inputBCD : in NATURAL;
	outputBCD : out STD_LOGIC_VECTOR(6 downto 0));
end BCD_7_DECODER;

architecture BCD_7_DECODER_ARCH of BCD_7_DECODER is	

begin
	process(inputBCD)
	begin
		case inputBCD is
			when 0 => outputBCD <= "0000001";
			when 1 => outputBCD <= "1001111";
			when 2 => outputBCD <= "0010010";
			when 3 => outputBCD <= "0000110";
			when 4 => outputBCD <= "1001100";
			when 5 => outputBCD <= "0100100";
			when 6 => outputBCD <= "0100000";
			when 7 => outputBCD <= "0001111";
			when 8 => outputBCD <= "0000000";
			when 9 => outputBCD <= "0000100"; 
			when others => outputBCD <= "1111111";
		end case;
	end process;  
	
end BCD_7_DECODER_ARCH;