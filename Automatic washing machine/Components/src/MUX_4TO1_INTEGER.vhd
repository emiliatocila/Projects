library IEEE;
use IEEE.STD_logic_1164.all; 
use IEEE.STD_LOGIC_UNSIGNED.all;

entity MUX_4TO1_INTEGER is
	port (SEL: in STD_LOGIC_VECTOR(1 downto 0);
	I0, I1, I2, I3: in INTEGER;
	Y: out INTEGER);
end MUX_4TO1_INTEGER;

architecture MUX_4TO1_INTEGER_ARCH of MUX_4TO1_INTEGER is

begin	 
	process(SEL, I0, I1, I2, I3)  
	begin
		case SEL is
			when "00" => Y <= I0;
			when "01" => Y <= I1;
			when "10" => Y <= I2;
			when "11" => Y <= I3; 
			when others => Y <= 0;
		end case;
	end process;
	
end MUX_4TO1_INTEGER_ARCH;