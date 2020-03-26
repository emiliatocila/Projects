library IEEE;
use IEEE.STD_LOGIC_1164.all;

entity NEXT_STATE_LOGIC is
	port(START, PRESPALARE, CLATIRE_SUPL, USA, CLK: in STD_LOGIC; 
	TEMPERATURA: in INTEGER;
	PRESPALARE_OUT, CLATIRE_SUPL_OUT, ACTIV, ROTIRE, ALIMENTARE, INCALZIRE, EVACUARE, USA_BLOCATA: out STD_LOGIC);
end NEXT_STATE_LOGIC;  

architecture NEXT_STATE_LOGIC_ARCH of NEXT_STATE_LOGIC is 
	
--stari
type state_type is (inactiv, blocare_usa, alim_apa, incalzire_apa, presp, evac, spalare, clt, clatire_sup, centrifugare, deblocare_usa);
signal STARE: state_type := inactiv; 

--contor pentru timer (durata fiecarei etape/stari)
signal COUNT: INTEGER := 0;

--timpi
constant TIMP_SPALARE_PRINCIPALA: INTEGER := 120;
constant TIMP_CLATIRE: INTEGER := 60;
constant TIMP_CENTRIFUGARE: INTEGER := 60;
constant TIMP_PRESPALARE: INTEGER := 60;
constant TIMP_APA: INTEGER := 6;	  

begin	
	next_state: process(CLK)
	
	variable TIMP_INCALZIRE: INTEGER := 0; 	
	variable CONDITII: STD_LOGIC_VECTOR(3 downto 0) := "0000";
	
		begin
			case TEMPERATURA is
				when 30 => TIMP_INCALZIRE := 3;
				when 40 => TIMP_INCALZIRE := 5;
				when 60 => TIMP_INCALZIRE := 9;
				when 90 => TIMP_INCALZIRE := 15; 
				when others => TIMP_INCALZIRE := 0;	
			end case;
		if (CLK = '1' and CLK'EVENT and CLK'LAST_VALUE = '0') then
			if (STARE = inactiv and START = '1' and USA = '1') then --daca masina a fost pornita si usa e inchisa
					STARE <= blocare_usa; --blocare usa 	
			end if;			
				case STARE is   
					when blocare_usa => 
						STARE <= alim_apa; --alimentare apa
					when alim_apa =>    
							if COUNT = TIMP_APA - 1 then
								 if CONDITII(1) = '0' then --nu am spalat inca
									STARE <= incalzire_apa; --incalzire apa	 
								elsif   CONDITII(2) = '0' then STARE <= clt; --clatire (am spalat, nu am clatit)
								elsif  (CLATIRE_SUPL = '1' and CONDITII(3) = '0') then
									STARE <= clatire_sup; --clatire suplimentara (am spalat, am clatit, nu am clatit suplimentar, avem clatire suplimentara de facut)
								end if;	
								COUNT <= 0;
							else COUNT <= COUNT + 1;					 			
							end if;	   
					when incalzire_apa => 
							if COUNT = TIMP_INCALZIRE - 1 then
								if PRESPALARE = '1' then --am incalzit apa
									if CONDITII(0) = '0' then --trebuie sa prespalam
										STARE <= presp; --prespalare  
									else STARE <= spalare; --am prespalat, urmeaza spalare
									end if;
								else STARE <= spalare; --nu trebuie sa prespalam 
								end if;
								COUNT <= 0;
							else COUNT <= COUNT + 1;
							end if;
					when presp =>   
							if COUNT = TIMP_PRESPALARE - 1 then
								STARE <= evac; --evacuare
								CONDITII(0) := '1';
								COUNT <= 0;
							else COUNT <= COUNT + 1;
							end if;
					when evac => 	  
							if COUNT = TIMP_APA - 1 then
									if (CLATIRE_SUPL = '0' and CONDITII(2) = '1') or (CLATIRE_SUPL = '1' and CONDITII(3) = '1') then
										STARE <= centrifugare; --centrifugare	
									else STARE <= alim_apa; --alimentare apa
									end if;
								COUNT <= 0;
							else COUNT <= COUNT + 1;
							end if;
					when spalare => 	  
							if COUNT = TIMP_SPALARE_PRINCIPALA - 1 then
								STARE <= evac; --evacuare
								CONDITII(1) := '1';
								COUNT <= 0;
							else COUNT <= COUNT + 1;
							end if;
					when clt => 	  
							if COUNT = TIMP_CLATIRE - 1 then
								STARE <= evac; --evacuare  
								CONDITII(2) := '1';	
								COUNT <= 0;
							else COUNT <= COUNT + 1;
							end if;	 
					when clatire_sup => 	  
							if COUNT = TIMP_CLATIRE - 1 then
								STARE <= evac; --evacuare  
								CONDITII(3) := '1';
								COUNT <= 0;
							else COUNT <= COUNT + 1;
							end if;
					when centrifugare => 	  
							if COUNT = TIMP_CENTRIFUGARE - 1 then
								STARE <= deblocare_usa; --deblocare_usa
								COUNT <= 0;
							else COUNT <= COUNT + 1;
							end if;				
					when deblocare_usa => 	  
							if COUNT = 6 - 1 then
								STARE <= inactiv; --inactiv	
								COUNT <= 0;
							else COUNT <= COUNT + 1;
							end if;
					when others => 
					CONDITII := "0000";
					end case; 
				end if;
	end process;	  
	
	outputs: process(STARE)
		begin
			case STARE is
				when inactiv =>				    
					ACTIV <= '0';
					ROTIRE <= '0';
					PRESPALARE_OUT <= '0'; 
					CLATIRE_SUPL_OUT <= '0';
					ALIMENTARE <= '0';
					INCALZIRE <= '0';
					EVACUARE <= '0';
					USA_BLOCATA <= '0';
				when blocare_usa => 				  
					ACTIV <= '1';
					ROTIRE <= '0'; 
					PRESPALARE_OUT <= '0'; 
					CLATIRE_SUPL_OUT <= '0';
					ALIMENTARE <= '0';
					INCALZIRE <= '0';
					EVACUARE <= '0';
					USA_BLOCATA <= '1';
				when alim_apa => 				  
					ACTIV <= '1';
					ROTIRE <= '0'; 
					PRESPALARE_OUT <= '0'; 
					CLATIRE_SUPL_OUT <= '0';
					ALIMENTARE <= '1';
					INCALZIRE <= '0';
					EVACUARE <= '0';
					USA_BLOCATA <= '1';
				when incalzire_apa => 		 		
					ACTIV <= '1';
					ROTIRE <= '0'; 
					PRESPALARE_OUT <= '0'; 
					CLATIRE_SUPL_OUT <= '0';
					ALIMENTARE <= '0';
					INCALZIRE <= '1';
					EVACUARE <= '0';
					USA_BLOCATA <= '1';
				when presp => 				
					ACTIV <= '1';
					ROTIRE <= '1';	 
					PRESPALARE_OUT <= '1'; 
					CLATIRE_SUPL_OUT <= '0';
					ALIMENTARE <= '0';
					INCALZIRE <= '0';
					EVACUARE <= '0';
					USA_BLOCATA <= '1';
				when evac => 				
					ACTIV <= '1';
					ROTIRE <= '0'; 
					PRESPALARE_OUT <= '0'; 
					CLATIRE_SUPL_OUT <= '0';
					ALIMENTARE <= '0';
					INCALZIRE <= '0';
					EVACUARE <= '1';
					USA_BLOCATA <= '1';	
				when spalare => 				
					ACTIV <= '1';
					ROTIRE <= '1'; 
					PRESPALARE_OUT <= '0'; 
					CLATIRE_SUPL_OUT <= '0';
					ALIMENTARE <= '0';
					INCALZIRE <= '0';
					EVACUARE <= '0';
					USA_BLOCATA <= '1';
				when clt => 				
					ACTIV <= '1';
					ROTIRE <= '1'; 
					PRESPALARE_OUT <= '0'; 
					CLATIRE_SUPL_OUT <= '0';
					ALIMENTARE <= '0';
					INCALZIRE <= '0';
					EVACUARE <= '0';
					USA_BLOCATA <= '1';	
				when clatire_sup => 				
					ACTIV <= '1';
					ROTIRE <= '1';
					PRESPALARE_OUT <= '0'; 
					CLATIRE_SUPL_OUT <= '1';
					ALIMENTARE <= '0';
					INCALZIRE <= '0';
					EVACUARE <= '0';
					USA_BLOCATA <= '1';
				when centrifugare => 				
					ACTIV <= '1';
					ROTIRE <= '1';
					PRESPALARE_OUT <= '0'; 
					CLATIRE_SUPL_OUT <= '0';
					ALIMENTARE <= '0';
					INCALZIRE <= '0';
					EVACUARE <= '0';
					USA_BLOCATA <= '1';
				when deblocare_usa =>
					ACTIV <= '1';
					ROTIRE <= '0';	
					PRESPALARE_OUT <= '0'; 
					CLATIRE_SUPL_OUT <= '0';
					ALIMENTARE <= '0';
					INCALZIRE <= '0';
					EVACUARE <= '0';
					USA_BLOCATA <= '1';	
				when others => 
					ACTIV <= '0';
					ROTIRE <= '0';
					PRESPALARE_OUT <= '0'; 
					CLATIRE_SUPL_OUT <= '0';
					ALIMENTARE <= '0';
					INCALZIRE <= '0';
					EVACUARE <= '0';
					USA_BLOCATA <= '0';
				end case;
	end process;  
	
end NEXT_STATE_LOGIC_ARCH; 
