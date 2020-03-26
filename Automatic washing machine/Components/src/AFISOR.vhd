library IEEE;
use IEEE.STD_LOGIC_1164.all;
use IEEE.STD_LOGIC_UNSIGNED.all; 
use IEEE.NUMERIC_STD.all;

entity AFISOR is
	port (CLK: in STD_LOGIC;	   
	TIMP: in NATURAL;
	UNITATI, ZECI, SUTE, MII: in STD_LOGIC_VECTOR(3 downto 0);
	A_TO_G: out STD_LOGIC_VECTOR(6 downto 0);
	AN: out STD_LOGIC_VECTOR(3 downto 0));
end AFISOR;

architecture AFISOR_ARH of AFISOR is  

--signal DIGIT: STD_LOGIC_VECTOR(3 downto 0) := "0000";	 
signal DIGIT: NATURAL;
signal COUNT: STD_LOGIC_VECTOR(1 downto 0) := "00";
signal AEN: STD_LOGIC_VECTOR(3 downto 0) := "0001"; --cifra 0 mereu pornita 

component BCD_7_DECODER
	port(inputBCD : in NATURAL;
	outputBCD : out STD_LOGIC_VECTOR(6 downto 0));
end component;

begin	
	
	ACTIVARE_ANOZI:process(CLK)
	begin 
		if (TIMP <= 9) then
			AEN(0) <= '1';
			AEN(1) <= '0';
			AEN(2) <= '0'; 
			AEN(3) <= '0';
		elsif (TIMP < 100) then
			AEN(0) <= '1';
			AEN(1) <= '1';	
			AEN(2) <= '0'; 
			AEN(3) <= '0';
		elsif (TIMP < 1000) then
			AEN(0) <= '1';
			AEN(1) <= '1';
			AEN(2) <= '1';  
			AEN(3) <= '0';
		elsif (TIMP < 10000) then
			AEN(0) <= '1';
			AEN(1) <= '1';
			AEN(2) <= '1'; 
			AEN(3) <= '1';
		end if;
	end process ACTIVARE_ANOZI;
	
	--numarator pe 2 biti
	CTR: process(CLK)
	begin
		if (COUNT = "UU") then
			COUNT <= "00";
		elsif (CLK'EVENT and CLK = '1' and CLK'LAST_VALUE = '0') then
			COUNT <= COUNT + 1;
		end if;
	end process;

	--MUX4
	with COUNT select
		DIGIT <= to_integer(unsigned(unitati)) when "00", to_integer(unsigned(zeci)) when "01", to_integer(unsigned(sute)) when "10", to_integer(unsigned(mii)) when others;
	  
	--BCD_7SEGM
	BCD: BCD_7_DECODER port map (DIGIT, A_TO_G);
	
	--selectare anod
	ANODE: process(COUNT)
	begin
		if (AEN(conv_integer(COUNT)) = '1') then
			AN <= (others => '1');
			AN(conv_integer(COUNT)) <= '0';
		else
			AN <= "1111";
		end if;
	end process;
	
end AFISOR_ARH;
