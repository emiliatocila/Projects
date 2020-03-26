library IEEE;
use IEEE.STD_logic_1164.all; 
use IEEE.STD_LOGIC_UNSIGNED.all;

entity MUX_PROGRAM_PRESP_CLT is
	port (SEL: in STD_LOGIC_VECTOR(2 downto 0);
	I0, I1, I2, I3, I4: in STD_LOGIC;
	Y: out STD_LOGIC);
end MUX_PROGRAM_PRESP_CLT;

architecture MUX_PROGRAM_PRESP_CLT_ARCH of MUX_PROGRAM_PRESP_CLT is	  

begin	 
	process (SEL, I0, I1, I2, I3)  
	begin
		case SEL is
			when "000" => Y <= I0;
			when "001" => Y <= I1;
			when "010" => Y <= I2; 
			when "011" => Y <= I3;
			when others => Y <= I4;
		end case;
	end process; 
	
end MUX_PROGRAM_PRESP_CLT_ARCH;
	
