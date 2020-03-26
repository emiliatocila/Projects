.386
.model flat, stdcall
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;includem biblioteci, si declaram ce functii vrem sa importam
includelib msvcrt.lib
extern exit: proc
extern malloc: proc
extern memset: proc
extern printf: proc

includelib canvas.lib
extern BeginDrawing: proc
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;declaram simbolul start ca public - de acolo incepe executia
public start
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;sectiunile programului, date, respectiv cod
.data
;final_turn_matrix declaram date
window_title DB "5 IN A ROW",0
area_width EQU 640
area_height EQU 480
area DD 0

X0 DD 120 ;coltul unde incepe matricea jocului, matricea va fi de 10x10
Y0 DD 90 

n DD 10
nrpixeli DD 300
max_moves DD 100

optxopt DD 0
nouaxnoua DD 0
zecexzece DD 1

matrix DD 100 dup(0)

XI DD 0
YI DD 0

coordX DD 0
coordY DD 0


X_0_width EQU 30 ;dimensiunile unui patratel din matrice
X_0_height EQU 30

click_out DD 0
occupied DD 0
last_symb DD 0

color DD 0

nr_click DD 0 ;cand e numar par => X, impar => 0

counter DD 0 ; numara evenimentele de tip timer

winc DD 0 ;variabilele care determina ce fel de castig (pe verticala, pe orizontala, pe diagonale)
winl DD 0
wind1 DD 0
wind2 DD 0

endgame DD 0 ;marcheaza cand cineva a castigat
playagain DD 0 ;marcheaza cand cineva a apasat pe butonul de play again

arg1 EQU 8
arg2 EQU 12
arg3 EQU 16
arg4 EQU 20

symbol_width EQU 10
symbol_height EQU 20

format DB "%d", 13, 10, 0

include X_0.inc
include digits.inc
include letters.inc

.code
; procedura make_text afiseaza o litera sau o cifra la coordonatele date
; arg1 - simbolul de afisat (litera sau cifra)
; arg2 - pointer la vectorul de pixeli
; arg3 - pos_x
; arg4 - pos_y

;matrix: [][] = 0 > necompletat; = 1 > X; = 2 > 0

show macro n
		push n
		push offset format
		call printf
		add esp, 8
endm

make_text proc
	push ebp
	mov ebp, esp
	pusha
	
	mov eax, [ebp+arg1] ; citim simbolul de afisat
	cmp eax, 'A'
	jl make_digit
	cmp eax, 'Z'
	jg make_digit
	sub eax, 'A'
	lea esi, letters
	jmp draw_text
make_digit:
	cmp eax, '0'
	jl make_space
	cmp eax, '9'
	jg make_space
	sub eax, '0'
	lea esi, digits
	jmp draw_text
make_space:	
	mov eax, 26 ; de la 0 pana la 25 sunt litere, 26 e space
	lea esi, letters
	
draw_text:
	mov ebx, symbol_width
	mul ebx
	mov ebx, symbol_height
	mul ebx
	add esi, eax
	mov ecx, symbol_height
bucla_simbol_linii:
	mov edi, [ebp+arg2] ; pointer la matrixa de pixeli
	mov eax, [ebp+arg4] ; pointer la coord y
	add eax, symbol_height
	sub eax, ecx
	mov ebx, area_width
	mul ebx
	add eax, [ebp+arg3] ; pointer la coord x
	shl eax, 2 ; inmultim cu 4, avem un DWORD per pixel
	add edi, eax
	push ecx
	mov ecx, symbol_width
bucla_simbol_coloane:
	cmp byte ptr [esi], 0
	je simbol_pixel_alb
	mov dword ptr [edi], 0
	jmp simbol_pixel_next
simbol_pixel_alb:
	mov dword ptr [edi], 0FFF2F4h
simbol_pixel_next:
	inc esi
	add edi, 4
	loop bucla_simbol_coloane
	pop ecx
	loop bucla_simbol_linii
	popa
	mov esp, ebp
	pop ebp
	ret
make_text endp

; un macro ca sa apelam mai usor desenarea simbolului
make_text_macro macro symbol, drawArea, x, y
	push y
	push x
	push drawArea
	push symbol
	call make_text
	add esp, 16
endm

make_X0 proc
	push ebp
	mov ebp, esp
	pusha	
make_X:
	mov eax, [ebp + arg1]  ;citim simbolul de afisat
	cmp eax, 'X'
	jne make_0
	sub eax, 'X'
	lea esi, X_0
	mov color, 0A63A79h ;culoarea pentru simbolul X
	jmp draw_X0
make_0:	
	mov eax, 1   ;al doilea simbol din fisier
	mov color, 05946B2h ;culoarea pentru simbolul 0 
	lea esi, X_0
draw_X0:
	mov ebx, X_0_width
	mul ebx
	mov ebx, X_0_height
	mul ebx
	add esi, eax
	mov ecx, X_0_height
bucla_simbol_liniiX0:
	mov edi, [ebp+arg2] ; pointer la matricea de pixeli
	mov eax, [ebp+arg4] ; pointer la coord y
	add eax, X_0_height
	sub eax, ecx
	mov ebx, area_width
	mul ebx
	add eax, [ebp+arg3] ; pointer la coord x
	shl eax, 2 ; inmultim cu 4, avem un DWORD per pixel
	add edi, eax
	push ecx
	mov ecx, X_0_height
bucla_simbol_coloaneX0:
	cmp byte ptr [esi], 0
	je simbol_pixel_albX0
	mov eax, color
	mov dword ptr [edi], eax
	jmp simbol_pixel_nextX0
simbol_pixel_albX0:
	mov dword ptr [edi], 0C4B3D6h ; culoarea fundalului simbolurilor X si 0
simbol_pixel_nextX0:
	inc esi
	add edi, 4
	loop bucla_simbol_coloaneX0
	pop ecx
	loop bucla_simbol_liniiX0
	popa
	mov esp, ebp
	pop ebp
	ret
make_X0 endp

; un macro ca sa apelam mai usor desenarea simbolului X sau 0
make_text_macroX0 macro symbol, drawArea, x, y
	push y
	push x
	push drawArea
	push symbol
	call make_X0
	add esp, 16
endm

draw_vertical macro drawArea, x, y, lenght
local next_pixel, final
	pusha
	mov eax, y
	mov ebx, area_width
	mul ebx
	add eax, x ; y*area_width + x
	shl eax, 2 ; DWORD pentru pixel
	mov ecx, 0
	mov edx, 0
	mov ebx, drawArea
next_pixelV:
	mov dword ptr [ebx+eax], 0
	inc ecx 			;cati pixeli s-au pus
	add eax, 2560 		;640*4 (urmatorul rand) - DWORD pentru pixel
	cmp ecx, lenght
	je finalV
	inc ecx 			;ecx se decrementeaza in bucla
	loop next_pixelV
finalV:
	popa
endm

draw_horizontal macro drawArea, x, y, lenght
local next_pixel, final
	pusha
	mov eax, 0
	mov eax, y
	mov ebx, area_width
	mul ebx
	add eax, x
	shl eax, 2
	mov ecx, 0
	mov edx, 0
next_pixelH:
	mov ebx, drawArea
	add ebx, edx
	mov dword ptr [eax+ebx], 0
	inc ecx		;cati pixeli s-au pus
	add edx, 4	;urmatorul pixel
	cmp ecx, lenght
	jg finalH
	inc ecx 
	loop next_pixelH
finalH:
	popa
endm

play_again_inside proc ;verificam daca s-a efectuat un click in butonul de play again
	push ebp
	mov ebp, esp
	
	mov ebx, [ebp+8] ;x
	mov edx, [ebp+12];y
	
	push eax
	push ecx
	
	cmp ebx, 450
	jl false_pa
	cmp ebx, 550
	jg false_pa
	cmp edx, 120
	jl false_pa
	cmp edx, 140
	jg false_pa
	mov eax, 1
	mov playagain, eax   
	jmp out_inside_pa
false_pa:
	mov eax, 0
	mov playagain, eax
out_inside_pa:
	pop ecx
	pop eax
	mov esp, ebp
	pop ebp
	ret 8
play_again_inside endp	

opt_inside proc ;verificam daca s-a efectuat un click in butonul de 8x8
	push ebp
	mov ebp, esp
	
	mov ebx, [ebp+8] ;x
	mov edx, [ebp+12];y
	
	push eax
	push ecx
	
	cmp ebx, 450
	jl out_inside_o
	cmp ebx, 480
	jg out_inside_o
	cmp edx, 150
	jl out_inside_o
	cmp edx, 170
	jg out_inside_o
	mov eax, 1
	mov optxopt, eax
	mov nouaxnoua, 0
	mov zecexzece, 0
	mov max_moves, 64
	mov playagain, 1
out_inside_o:
	pop ecx
	pop eax
	mov esp, ebp
	pop ebp
	ret 8
opt_inside endp	

noua_inside proc ;verificam daca s-a efectuat un click in butonul de 9x9
	push ebp
	mov ebp, esp
	
	mov ebx, [ebp+8] ;x
	mov edx, [ebp+12];y
	
	push eax
	push ecx
	
	cmp ebx, 450
	jl out_inside_n
	cmp ebx, 480
	jg out_inside_n
	cmp edx, 180
	jl out_inside_n
	cmp edx, 200
	jg out_inside_n
	mov eax, 1
	mov nouaxnoua, eax
	mov optxopt, 0
	mov zecexzece, 0
	mov max_moves, 81	
	mov playagain, 1
out_inside_n:
	pop ecx
	pop eax
	mov esp, ebp
	pop ebp
	ret 8
noua_inside endp	

zece_inside proc ;verificam daca s-a efectuat un click in butonul de 10x10
	push ebp
	mov ebp, esp
	
	mov ebx, [ebp+8] ;x
	mov edx, [ebp+12];y
	
	push eax
	push ecx
	
	cmp ebx, 450
	jl out_inside_z
	cmp ebx, 500
	jg out_inside_z
	cmp edx, 210
	jl out_inside_z
	cmp edx, 230
	jg out_inside_z
	mov eax, 1
	mov zecexzece, eax  
	mov optxopt, 0
	mov nouaxnoua, 0
	mov max_moves, 100	
	mov playagain, 1
out_inside_z:
	pop ecx
	pop eax
	mov esp, ebp
	pop ebp
	ret 8
zece_inside endp	

inside proc ;verificam daca s-a efectuat un click in interiorul tablei
	push ebp
	mov ebp, esp
	
	mov ebx, [ebp+8] ;x
	mov edx, [ebp+12];y
	
	push eax
	push ecx
	
	mov esi, edx
	cmp ebx, X0
	jl false
	mov eax, n
	mov ecx, X_0_width
	mul ecx
	add eax, X0
	cmp ebx, eax
	jge false
	mov edx, esi
	cmp edx, Y0
	jl false
	mov eax, n
	mov ecx, X_0_width
	mul ecx
	add eax, Y0
	mov edx, esi
	cmp edx, eax
	jge false
	mov eax, 1
	mov click_out, eax   
	jmp out_inside
false:
	mov eax, 0
	mov click_out, eax
out_inside:
	pop ecx
	pop eax
	mov esp, ebp
	pop ebp
	ret 8
inside endp	

turn_matrix proc ;pune in matrice simbolul corespunzator in functie de zona in care am dat click si in functie de numarul de click-uri
	push ebp
	mov ebp, esp
	
	mov ebx, [ebp+8] ;x
	mov edx, [ebp+12];y
	
	;aflam coordonatele matricii noastre: (X-X0)/X_0_height = j; (Y-Y0)/X_0_width = i
	mov edi, X_0_height
	push edx ;salvam edx cand se modifica la impartire
	
	sub ebx, X0	
	mov eax, ebx
	xor edx, edx
	div edi
	mov YI, eax

	pop edx
	push edx
	
	sub edx, Y0
	mov eax, edx
	xor edx, edx
	div edi
	mov XI, eax
	
	pop edx
	
	mov ebx, XI
	mov edx, YI
	
	push edx
	xor edx, edx
	mov eax, 10
	mul ebx
	mov ebx, eax ;ebx = i*10
	pop edx	
	
	;verificam daca e ocupata pozitia
	add ebx, edx ;i*10+j
	mov esi, matrix[ebx*4] ;(i*10+j)*4 (pentru ca e DWORD)
	
	cmp esi, 0 ;verificam daca casuta e completata sau nu
	jne is_occupied ;e ocupata
	
	mov occupied, 0 ;marcam casuta ca fiind necompletata
	
	test nr_click, 1 ;verificam daca e randul lui X sau 0
	jnz is_X
	
is_0:
	mov eax, 2
	mov matrix[ebx*4], eax
	jmp final_turn_matrix
is_X:
	mov eax, 1
	mov matrix[ebx*4], eax
	jmp final_turn_matrix
is_occupied:
	;marcam casuta ca fiind completata pentru a nu desena peste ea
	mov occupied, 1
final_turn_matrix:
	mov esp, ebp
	pop ebp
	ret 8
turn_matrix endp

win_columns proc
	push ebp
	mov ebp, esp
	mov edx, 4 ;5 in a row
	mov ebx, 0 ;coloana, coloanele se afla la +4
	mov esi, 0 ;linia, liniile se afla la +40 (10*4)
	mov ecx, 10
	loop_jc:
		mov edx, 4 ;de fiecare data cand se trece la o coloana noua se initializeaza edx
		mov esi, 0 ;de fiecare data cand se trece la o coloana noua se initializeaza esi(liniile) pentru a incepe de la primul element
		mov edi, matrix[ebx+esi]
		mov last_symb, edi ;contorizeaza elementul anterior pentru comparatie
		add esi, 40 ;trecem la linia urmatoare
		push ecx
		mov ecx, 9 ;avem 10 linii, dar noi am trecut deja la linia urmatoare din cauza ca aveam nevoie de primul element de pe coloana la last_symb
		loop_ic:	
			mov edi, matrix[ebx+esi]
			cmp edi, 0 ;vedem daca casuta e necompletata, ne intereseaza doar daca e completata
			je n_dec_edxc
			cmp edi, last_symb ;daca e completata, comparam cu elementul anterior
			je dec_edxc
			n_dec_edxc: ;daca casuta e necompletata sau daca elementele nu sunt aceleasi, se reinitializeaza last_symb si edx devine iar 4; s-a rupt "lantul"
				mov last_symb, edi
				mov edx, 4
				jmp next_line
			dec_edxc:
				dec edx ;e acelasi simbol, deci decrementam edx
			next_line:
				add esi, 40
				cmp edx, 0 ;verificam daca sunt 5 casute consecutive cu acelasi simbol; daca nu, continuam cautarea
				je end_columns
			loop loop_ic
		pop ecx
		add ebx, 4 ;trecem la urmatoarea coloana
		loop loop_jc
		
	jmp no_winc ;am parcurs toata matricea si nu am gasit 5 elemente identice consecutive in coloana
		
	end_columns:
			test nr_click, 1
			jz X_winsc
			jnz O_winsc
	O_winsc:
		mov winc, 2
		jmp out_columns
	X_winsc:
		mov winc, 1
		jmp out_columns
	no_winc:
		mov winc, 0
	out_columns:
		mov esp, ebp
		pop ebp
		ret
win_columns endp

win_lines proc
	push ebp
	mov ebp, esp
	mov edx, 4
	mov ebx, 0 ;coloana, coloanele se afla la +4
	mov esi, 0 ;linia, liniile se afla la +40 (10*4)
	mov ecx, 10
	loop_il:
		mov edx, 4
		mov ebx, 0
		mov edi, matrix[ebx+esi]
		mov last_symb, edi
		add ebx, 4
		push ecx
		mov ecx, 9
		loop_jl:	
			mov edi, matrix[ebx+esi]
			cmp edi, 0
			je n_dec_edxl
			cmp edi, last_symb
			je dec_edxl
			n_dec_edxl:
				mov last_symb, edi
				mov edx, 4
				jmp next_column
			dec_edxl:
				dec edx
			next_column:
				add ebx, 4
				cmp edx, 0
				je end_lines
			loop loop_jl
		pop ecx
		add esi, 40
		loop loop_il
		
	jmp no_winl
		
	end_lines:
			test nr_click, 1
			jz X_winsl
			jnz O_winsl
	O_winsl:
		mov winl, 2
		jmp out_lines
	X_winsl:
		mov winl, 1
		jmp out_lines
	no_winl:
		mov winl, 0
	out_lines:
		mov esp, ebp
		pop ebp
		ret
win_lines endp

win_d1 proc
	push ebp
	mov ebp, esp
	mov edx, 4
	mov ebx, 24;coloana, coloanele se afla la +4, 20 pentru ca incepem de la [0][5] (5*4)
	mov esi, 0 ;linia, liniile se afla la +40 (10*4)
	;urmatorul element din diagonala e la +44 (o linie+un element)
	mov ecx, 6
	mov eax, 3
	loop_d1l0:
		sub ebx, 4
		push ebx
		mov esi, 0
		mov edi, matrix[ebx+esi]
		mov last_symb, edi
		add ebx, 44
		push ecx
		inc eax
		mov ecx, eax
		loop_d1c0:
			mov edi, matrix[ebx+esi]
			cmp edi, 0
			je n_dec_edxd10
			cmp edi, last_symb
			je dec_edxd10
			n_dec_edxd10:
				mov last_symb, edi
				mov edx, 4
				jmp next_ed10
			dec_edxd10:
				dec edx
			next_ed10:
				add ebx, 44
				cmp edx, 0
				je end_d1
			loop loop_d1c0			
		pop ecx
		pop ebx
		loop loop_d1l0
	mov ecx, 5
	mov eax, 9
	mov esi, 40
	loop_d1l:
		mov ebx, 0
		mov edi, matrix[ebx+esi]
		mov last_symb, edi
		add ebx, 44
		push ecx
		dec eax
		mov ecx, eax
		loop_d1c:
			mov edi, matrix[ebx+esi]
			cmp edi, 0
			je n_dec_edxd1
			cmp edi, last_symb
			je dec_edxd1
			n_dec_edxd1:
				mov last_symb, edi
				mov edx, 4
				jmp next_ed1
			dec_edxd1:
				dec edx
			next_ed1:
				add ebx, 44
				cmp edx, 0
				je end_d1			
			loop loop_d1c
		pop ecx
		add esi, 40
		loop loop_d1l
		
	jmp no_wind1
		
	end_d1:
			test nr_click, 1
			jz X_winsd1
			jnz O_winsd1
	O_winsd1:
		mov wind1, 2
		jmp out_d1
	X_winsd1:
		mov wind1, 1
		jmp out_d1
	no_wind1:
		mov wind1, 0
	out_d1:
		mov esp, ebp
		pop ebp
		ret
win_d1 endp

win_d2 proc
	push ebp
	mov ebp, esp
	mov edx, 4
	mov ebx, 12;coloana, coloanele se afla la +4, 16 pentru ca incepem de la [0][4] (4*4)
	mov esi, 0 ;linia, liniile se afla la +40 (10*4)
	;urmatorul element din diagonala e la +36 (o linie-un element)
	mov ecx, 6
	mov eax, 3
	loop_d1l0:
		add ebx, 4
		push ebx
		mov esi, 0
		mov edi, matrix[ebx+esi]
		mov last_symb, edi
		add ebx, 36
		push ecx
		inc eax
		mov ecx, eax
		loop_d1c0:
			mov edi, matrix[ebx+esi]
			cmp edi, 0
			je n_dec_edxd10
			cmp edi, last_symb
			je dec_edxd10
			n_dec_edxd10:
				mov last_symb, edi
				mov edx, 4
				jmp next_ed10
			dec_edxd10:
				dec edx
			next_ed10:
				add ebx, 36
				cmp edx, 0
				je end_d1
			loop loop_d1c0			
		pop ecx
		pop ebx
		loop loop_d1l0
	mov ecx, 5
	mov eax, 9
	mov esi, 76
	loop_d1l:
		mov ebx, 0
		mov edi, matrix[ebx+esi]
		mov last_symb, edi
		add ebx, 36
		push ecx
		dec eax
		mov ecx, eax
		loop_d1c:
			mov edi, matrix[ebx+esi]
			cmp edi, 0
			je n_dec_edxd1
			cmp edi, last_symb
			je dec_edxd1
			n_dec_edxd1:
				mov last_symb, edi
				mov edx, 4
				jmp next_ed1
			dec_edxd1:
				dec edx
			next_ed1:
				add ebx, 36
				cmp edx, 0
				je end_d1			
			loop loop_d1c
		pop ecx
		add esi, 40
		loop loop_d1l
		
	jmp no_wind1
		
	end_d1:
			test nr_click, 1
			jz X_winsd1
			jnz O_winsd1
	O_winsd1:
		mov wind1, 2
		jmp out_d1
	X_winsd1:
		mov wind2, 1
		jmp out_d1
	no_wind1:
		mov wind2, 0
	out_d1:
		mov esp, ebp
		pop ebp
		ret
win_d2 endp

; functia de desenare - se apeleaza la fiecare click
; sau la fiecare interval de 200ms in care nu s-a dat click
; arg1 - evt (0 - initializare, 1 - click, 2 - s-a scurs intervalul fara click)
; arg2 - x
; arg3 - y
draw proc
	push ebp
	mov ebp, esp
	pusha
	
	cmp endgame, 1 ;verificam daca a castigat cineva, daca da, se verifica in continuu daca s-a apasat play again
	jne not_end_game
see_play_again:
	cmp playagain, 1
	je redo_matrix ;daca s-a apasat play again, se goleste matricea
	jne sfarsitJoc
not_end_game:
	mov eax, [ebp+arg1]
	cmp eax, 1
	jz evt_click
	cmp eax, 2
	jz afisare_litere

redo_game:
	;mai jos e codul care intializeaza fereastra cu pixeli albi
	mov eax, area_width
	mov ebx, area_height
	mul ebx
	shl eax, 2
	push eax
	push 0FFF2F4h
	push area
	call memset
	add esp, 12
	jmp afisare_litere

sfarsitJoc:
	mov eax, [ebp+arg1]
	cmp eax, 1 ;verificam daca dupa ce a castigat cineva s-a dat click pe play again, daca s-a dat click oriunde altundeva nu se intampla nimic
	jnz skip
play_again_after_end_game:
	mov ebx, [ebp+arg2] ;x
	mov edx, [ebp+arg3] ;y
	pusha
	push edx
	push ebx
	call play_again_inside ;verificam daca s-a dat click pe butonul de play again
	cmp playagain, 1
	jz redo_matrix
	jnz skip2
	popa
skip:
	cmp eax, 2
	jz afisare_litere
skip2:
	jmp afisare_litere
	
evt_click:
	inc nr_click
	mov ebx, [ebp+arg2] ;x
	mov edx, [ebp+arg3] ;y
	
	pusha
	push edx
	push ebx
	call play_again_inside ;jucatorii pot apasa play again si in timpul jocului daca doresc
	cmp playagain, 1
	jz redo_matrix
	popa
	
	pusha
	push edx
	push ebx
	call opt_inside 
	popa
	
	pusha
	push edx
	push ebx
	call noua_inside
	popa
	
	pusha
	push edx
	push ebx
	call zece_inside 
	popa
	
	cmp optxopt, 1
	jz make_opt
	cmp nouaxnoua, 1
	jz make_noua	
	jnz make_zece
	
make_opt:
	mov n, 8
	mov nrpixeli, 240
	jmp skip_par1
make_noua:
	mov n, 9
	mov nrpixeli, 270
	jmp skip_par1
make_zece:
	mov n, 10
	mov nrpixeli, 300

skip_par1:
	
	pusha
	push edx
	push ebx
	call inside ;se verifica daca s-a dat click in interiorul tablei
	popa
	
	cmp playagain, 1
	jz redo_matrix

	cmp click_out, 1
	jne incrementare_nr_click ;daca nu s-a dat click in interiorul tablei, nu se intampla nimic, ci doar se incrementeaza contorul de click-uri pentru a nu strica succesiunea de simboluri
	
	push edx
	push ebx
	call turn_matrix ;pune in matricea construita de noi codul simbolurilor in locurile corespunzatoare in functie de unde am dat click
	
	;aflam daca e ocupata sau nu casuta
	cmp occupied, 1
	je incrementare_nr_click ;aceeasi idee ca si la click_out; daca casuta era ocupata nu se intampla nimic, doar se incrementeaza numarul de click-uri
	
	mov edi, X_0_height 
	;in eax se afla 1 sau 2
	
	push eax
	;in functie de pozitia din matrice se deseneaza simbolul pe tabla noastra: 
	;coordX=YI*X_0_height+X0 + 1 (pentru a nu desena peste liniile si coloanele din tabla), coordY=XI*X_0_height+Y0
	xor edx, edx
	mov eax, YI
	mul edi
	add eax, X0
	inc eax
	mov coordX, eax
	xor edx, edx
	mov eax, XI
	mul edi
	add eax, Y0
	inc eax
	mov coordY, eax
	
	pop eax
	
	cmp eax, 1
	je make_0_table
	
make_X_table:
	make_text_macroX0 'X', area, coordX, coordY
	dec max_moves
	cmp max_moves, 0 ;verificam daca am umplut tabla, daca da, inseamna egalitate
	je mesaj_draw
	jmp check_win ;daca nu e egalitate, verificam daca a castigat cineva
	
make_0_table:
	make_text_macroX0 '0', area, coordX, coordY
	dec max_moves
	cmp max_moves, 0
	je mesaj_draw
	jmp check_win
	
check_win: ;verificam toate cazurile in care ar fi putut castiga cineva
	call win_columns
	call win_lines
	call win_d1
	call win_d2
	cmp winc, 1
	je mesaj_X_wins
	cmp winc, 2
	je mesaj_0_wins
	cmp winl, 1
	je mesaj_X_wins
	cmp winl, 2
	je mesaj_0_wins
	cmp wind1, 1
	je mesaj_X_wins
	cmp wind1, 2
	je mesaj_0_wins
	cmp wind2, 1
	je mesaj_X_wins
	cmp wind2, 2
	je mesaj_0_wins
	jmp afisare_litere
	
incrementare_nr_click:
	inc nr_click
	jmp afisare_litere
	
mesaj_X_wins:
	mov endgame, 1
	make_text_macro 'X', area, 450, 90
	
	make_text_macro 'W', area, 470, 90
	make_text_macro 'O', area, 480, 90
	make_text_macro 'N', area, 490, 90
	jmp afisare_litere
mesaj_0_wins:
	mov endgame, 1
	make_text_macro '0', area, 450, 90
	
	make_text_macro 'W', area, 470, 90
	make_text_macro 'O', area, 480, 90
	make_text_macro 'N', area, 490, 90
	jmp afisare_litere
	
redo_matrix: ;reinitializam toate variabilele si golim matricea, apoi golim si tabla
	cmp optxopt, 1
	jz max_moves_opt
	cmp nouaxnoua, 1
	jz max_moves_noua
	mov max_moves, 100
max_moves_opt:
	mov max_moves, 64
	jmp skip_max_moves
max_moves_noua:
	mov max_moves, 81
skip_max_moves:
	mov click_out, 0
	mov occupied, 0
	mov last_symb, 0
	mov nr_click, 0
	mov winc, 0
	mov winl, 0
	mov wind1, 0
	mov wind2, 0
	mov endgame, 0
	mov playagain, 0
	mov ecx, 100
	mov esi, 0
	loop_reset_matrix:
		mov ebx, ecx
		dec ebx
		mov matrix[ebx*4], esi
		loop loop_reset_matrix
	jmp redo_game
	jmp afisare_litere
	
mesaj_draw:
	mov endgame, 1
	make_text_macro 'D', area, 450, 90
	make_text_macro 'R', area, 460, 90
	make_text_macro 'A', area, 470, 90
	make_text_macro 'W', area, 480, 90
	
afisare_litere:
	;scriem un mesaj
	make_text_macro '5', area, 120, 40
	
	make_text_macro 'I', area, 140, 40
	make_text_macro 'N', area, 150, 40
	
	make_text_macro 'A', area, 170, 40
	
	make_text_macro 'R', area, 190, 40
	make_text_macro 'O', area, 200, 40
	make_text_macro 'W', area, 210, 40
	
	make_text_macro 'P', area, 450, 120
	make_text_macro 'L', area, 460, 120
	make_text_macro 'A', area, 470, 120
	make_text_macro 'Y', area, 480, 120
	
	make_text_macro 'A', area, 500, 120
	make_text_macro 'G', area, 510, 120
	make_text_macro 'A', area, 520, 120
	make_text_macro 'I', area, 530, 120
	make_text_macro 'N', area, 540, 120
	
	make_text_macro '8', area, 450, 150
	make_text_macro 'X', area, 460, 150
	make_text_macro '8', area, 470, 150
	
	make_text_macro '9', area, 450, 180
	make_text_macro 'X', area, 460, 180
	make_text_macro '9', area, 470, 180
	
	make_text_macro '1', area, 450, 210
	make_text_macro '0', area, 460, 210	
	make_text_macro 'X', area, 470, 210
	make_text_macro '1', area, 480, 210
	make_text_macro '0', area, 490, 210	
	
	make_text_macro 'T', area, 120, 420
	make_text_macro 'O', area, 130, 420
	make_text_macro 'C', area, 140, 420
	make_text_macro 'I', area, 150, 420
	make_text_macro 'L', area, 160, 420
	make_text_macro 'A', area, 170, 420

	make_text_macro 'E', area, 190, 420
	make_text_macro 'M', area, 200, 420
	make_text_macro 'I', area, 210, 420
	make_text_macro 'L', area, 220, 420
	make_text_macro 'I', area, 230, 420
	make_text_macro 'A', area, 240, 420
	
	pusha
	mov ebx, 90
	push eax
	mov eax, n
	inc eax
	mov ecx, eax
	pop eax
	loop_horizontal2:
	draw_horizontal area, X0, ebx, nrpixeli
	add ebx, X_0_width
	loop loop_horizontal2

	mov esi, 120
	push eax
	mov eax, n
	inc eax
	mov ecx, eax
	pop eax
	loop_vertical2:
	push ecx
	draw_vertical area, esi, Y0, nrpixeli
	pop ecx
	add esi, X_0_width
	loop loop_vertical2
	popa

final_draw:
	popa
	mov esp, ebp
	pop ebp
	ret
draw endp

start:
	;alocam memorie pentru zona de desenat
	mov eax, area_width
	mov ebx, area_height
	mul ebx
	shl eax, 2
	push eax
	call malloc
	add esp, 4
	mov area, eax
	;apelam functia de desenare a ferestrei
	; typedef void (*DrawFunc)(int evt, int x, int y);
	; void __cdecl BeginDrawing(const char *title, int width, int height, unsigned int *area, DrawFunc draw);
	push offset draw
	push area
	push area_height
	push area_width
	push offset window_title
	call BeginDrawing
	add esp, 20
	
	;terminarea programului
	push 0
	call exit
end start
