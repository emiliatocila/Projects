library IEEE;
use IEEE.STD_LOGIC_1164.all; 
use IEEE.STD_LOGIC_UNSIGNED.all;
use IEEE.NUMERIC_STD.all;

entity BIN_TO_BCD is
	port(BINAR: in STD_LOGIC_VECTOR(11 downto 0);
	UNITATI: out STD_LOGIC_VECTOR(3 downto 0);
	ZECI: out STD_LOGIC_VECTOR(3 downto 0);
	SUTE: out STD_LOGIC_VECTOR(3 downto 0);	
	MII: out STD_LOGIC_VECTOR(3 downto 0));
end BIN_TO_BCD;

architecture A_BIN_TO_BCD of BIN_TO_BCD is
begin
	TO_BCD:process(BINAR)
	variable TEMP: STD_LOGIC_VECTOR(11 downto 0);	
	variable BCD: UNSIGNED(15 downto 0) := (others => '0');
	--mii: bcd(15 downto 12); sute: bcd(11 donwto 8); zeci: bcd(7 downto 4); unitati: bcd(3 downto 0)  
	begin
		BCD := (others => '0');
		TEMP(11 downto 0) := BINAR;
		for I in 0 to 11 loop
			if BCD(3 downto 0) > 4 then	
				BCD(3 downto 0) := BCD(3 downto 0) + 3;
			end if;
			if BCD(7 downto 4) > 4 then	
				BCD(7 downto 4) := BCD(7 downto 4) + 3;
			end if;
			if BCD(11 downto 8) > 4 then	
				BCD(11 downto 8) := BCD(11 downto 8) + 3;
			end if;
			BCD := BCD(14 downto 0) & TEMP(11);
			TEMP := TEMP(10 downto 0) & '0';
		end loop;
		
		UNITATI <= STD_LOGIC_VECTOR(BCD(3 downto 0)); 
		ZECI <= STD_LOGIC_VECTOR(BCD(7 downto 4));
		SUTE <= STD_LOGIC_VECTOR(BCD(11 downto 8));
		MII <= STD_LOGIC_VECTOR(BCD(15 downto 12));	
		
	end process TO_BCD;
	
	end A_BIN_TO_BCD;