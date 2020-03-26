library IEEE;				
use IEEE.STD_LOGIC_1164.all;
use IEEE.STD_LOGIC_UNSIGNED.all;

entity GET_DATA is
	port (MODE, BUTON_P, BUTON_T, BUTON_V, PRESPALARE_MANUAL, CLATIRE_MANUAL: in STD_LOGIC;
	PRESPALARE, CLATIRE_SUPL: out STD_LOGIC;
	VITEZA, TEMPERATURA: out INTEGER);
end GET_DATA;

architecture GET_DATA_ARCH of GET_DATA is 

--programul selectat
signal OUT_CTR_AUTO: STD_LOGIC_VECTOR(2 downto 0);	

--temperatura 
signal T_OUT_MANUAL, T_OUT_AUTO, SEL_TEMPERATURA: STD_LOGIC_VECTOR(1 downto 0);	

--viteza
signal V_OUT_MANUAL, V_OUT_AUTO, SEL_VITEZA: STD_LOGIC_VECTOR(1 downto 0);

--prespalare si clatire intermediare 
signal PRESPALARE_AUTO, CLATIRE_SUPL_AUTO: STD_LOGIC;

component COUNTER_GET
	generic (limita: INTEGER := 4;
	N : integer := 1);
	port (CLR, CLK: in STD_LOGIC;
	Q: out STD_LOGIC_VECTOR((N - 1) downto 0));
end component; 	 

component MUX_4TO1_INTEGER
	port (SEL: in STD_LOGIC_VECTOR(1 downto 0);
	I0, I1, I2, I3: in INTEGER;
	Y: out INTEGER);
end component;

component MUX_PROGRAM
	port (SEL: in STD_LOGIC_VECTOR(2 downto 0);
	I0, I1, I2, I3, I4: in  STD_LOGIC_VECTOR(1 downto 0);
	Y: out STD_LOGIC_VECTOR(1 downto 0));
end component;	

component MUX_PROGRAM_PRESP_CLT
	port (SEL: in STD_LOGIC_VECTOR(2 downto 0);
	I0, I1, I2, I3, I4: in STD_LOGIC;
	Y: out STD_LOGIC);
end component;

component MUX_2TO1
	generic (N: integer := 2);
	port (SEL: in STD_LOGIC;
	A, B: in STD_LOGIC_VECTOR((N - 1) downto 0);
	Y: out STD_LOGIC_VECTOR((N - 1) downto 0));
end component; 

component MUX_2TO1_BIT 
	port (SEL: in STD_LOGIC;
	A, B: in STD_LOGIC;
	Y: out STD_LOGIC);
end component;
	
begin
	
NR_SEL_AUTO: COUNTER_GET generic map (4, 3) port map ('0', BUTON_P, OUT_CTR_AUTO);
	
NR_SEL_MANUAL_TEMP: COUNTER_GET generic map (3, 2) port map ('0', BUTON_T, T_OUT_MANUAL);
MUX_AUTO_TEMP: MUX_PROGRAM port map (OUT_CTR_AUTO, "00", "10", "01", "01", "11", T_OUT_AUTO); 
MUX_TEMP: MUX_2TO1 generic map (2) port map (MODE, T_OUT_MANUAL, T_OUT_AUTO, SEL_TEMPERATURA);
MUX_MANUAL_TEMP: MUX_4TO1_INTEGER port map (SEL_TEMPERATURA, 30, 40, 60, 90, TEMPERATURA);	

NR_SEL_MANUAL_VIT: COUNTER_GET generic map (2, 2) port map ('0', BUTON_V, V_OUT_MANUAL);
MUX_AUTO_VIT: MUX_PROGRAM port map (OUT_CTR_AUTO, "10", "00", "01", "01", "10", V_OUT_AUTO); 
MUX_VITEZA: MUX_2TO1 generic map (2) port map (MODE, V_OUT_MANUAL, V_OUT_AUTO, SEL_VITEZA);
MUX_MANUAL_VIT: MUX_4TO1_INTEGER port map (SEL_VITEZA, 800, 1000, 1200, 0, VITEZA);	

MUX_AUTO_PRESP: MUX_PROGRAM_PRESP_CLT port map (OUT_CTR_AUTO, '0', '0', '0', '1', '0', PRESPALARE_AUTO);
MUX_AUTO_CLT_SUP: MUX_PROGRAM_PRESP_CLT port map (OUT_CTR_AUTO, '0', '0', '1', '0', '1', CLATIRE_SUPL_AUTO);
MUX_MANUAL_PRESP: MUX_2TO1_BIT port map (MODE, PRESPALARE_MANUAL, PRESPALARE_AUTO, PRESPALARE);
MUX_MANUAL_CLT_SUP: MUX_2TO1_BIT port map (MODE, CLATIRE_MANUAL, CLATIRE_SUPL_AUTO, CLATIRE_SUPL);

end GET_DATA_ARCH;
