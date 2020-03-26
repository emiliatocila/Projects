library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.numeric_std.ALL;

entity FREQUENCY_DIVIDER is
port (CLK,reset: in std_logic;
      CLK_OUT: out std_logic);
end FREQUENCY_DIVIDER;	  

architecture FREQUENCY_DIVIDER_ARCH of FREQUENCY_DIVIDER is
	signal count: integer := 1;
	signal toggle : std_logic := '0';
begin
	process(CLK,reset)
	begin
		if(reset = '1') then
			count <= 1;
			toggle <= '0';
		elsif(CLK'event and CLK = '1') then
			count <= count + 1;
		if (count = 25000) then
			toggle <= NOT toggle;
			count <= 1;
		end if;
		end if;
		CLK_out <= toggle;
	end process;
end FREQUENCY_DIVIDER_ARCH;