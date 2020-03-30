library IEEE; 
use IEEE.STD_LOGIC_1164.all;
use IEEE.STD_LOGIC_UNSIGNED.all;   


entity TIME_CALC is
	port (TEMPERATURA, TIMP_SPALARE_PRINCIPALA, TIMP_CLATIRE, TIMP_CENTRIFUGARE, TIMP_PRESPALARE, TIMP_APA: in INTEGER;
	PRESPALARE, CLATIRE_SUPL: in STD_LOGIC;
	TIMP: out NATURAL);
end TIME_CALC;

architecture TIME_CALC_ARCH of TIME_CALC is	

begin
	process (TEMPERATURA, PRESPALARE, CLATIRE_SUPL)
	
	variable TIMP_INCALZIRE: INTEGER := 0;
	variable SUMA_TIMP: INTEGER := 0; 
	
	begin	
		case TEMPERATURA is
			when 30 => TIMP_INCALZIRE := 3;
			when 40 => TIMP_INCALZIRE := 5;
			when 60 => TIMP_INCALZIRE := 9;
			when 90 => TIMP_INCALZIRE := 15; 
			when others => TIMP_INCALZIRE := 0;
		end case;	  
		SUMA_TIMP := TIMP_INCALZIRE + TIMP_SPALARE_PRINCIPALA + TIMP_CLATIRE + TIMP_CENTRIFUGARE + 4 * TIMP_APA;
		if (PRESPALARE = '1') then
			SUMA_TIMP := SUMA_TIMP + TIMP_PRESPALARE + 2 * TIMP_APA;
		end if;
		if (CLATIRE_SUPL = '1') then
			   SUMA_TIMP := SUMA_TIMP + TIMP_CLATIRE + 2 * TIMP_APA + TIMP_INCALZIRE;
		end if;	 
		TIMP <= SUMA_TIMP;
	end process; 
	
end TIME_CALC_ARCH;