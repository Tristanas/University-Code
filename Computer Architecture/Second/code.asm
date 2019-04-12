.model small
	ASSUME CS:kodas, DS:duomenys, SS:stekas
	
stekas segment word stack 'STACK'
       dw 400h dup (00)               ; stekas -> 2 Kb
stekas ends

duomenys segment para public 'DATA'
fout:
	db 20, 00, 20 dup (00)
input:
	db 20 dup (00)
neil:
  db 0Dh, 0Ah, '$' 
max:
  db 00 ;didziausias skaitmuo
intro:
	db 'Vilius Minkevicius, PS 1 kursas, 2 grupe', 0Dh, 0Ah, 'Iveskite isvesties pavadinima.',0Dh,0Ah,'$'
gerai:
	db 'File rastas',0Dh,0Ah,'$'
negerai:
	db 'Nerastas file',0Dh,0Ah,'$'
blogai:
	db 'Input file nerastas', 0Dh,0Ah,'$'
inhan:	;input handle
	dw 00
outhan:
	dw 00 ;output handle
simbolis:
	db 00
Bbufferis:
	db 00, 00, 00
uzd:
	db 00
Auzd:
	db 'A uzduotis', 0dh, 0Ah, '$'
Buzd:
	db 'B uzduotis', 0dh, 0Ah, '$'
bitreg:
	db 'alcldlblahchdhbh'
bytereg:
	db 'axcxdxbxbpspsidi'
move:
	db 'mov'

duomenys ends
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
idek macro bit1, bit2	;pirmaji bita perkelia i antrojo vieta
		mov ax, bit1
		mov cl, 08h
		div cl
		mov bx, ax	;iš bx: bh - kelintas bitas, bl - kelintame baite
		mov ax, bit2	
		div cl	;į ax:	ah - bitas, al - baitas, kur keliamas bitas
		
		mov ch, 01h
		mov cl, bh
		shl ch, cl
		xor bh, bh
		and ch, byte ptr [Bbufferis + bx]	;tikriname, ar keliamas bitas yra 1, ar 0
		shr ch, cl
		mov cl, ah
		mov dl, 01h
		shl dl, cl
		shl ch, cl	;ch = keliamas bitas
		mov dh, 0ffh
		sub dh, dl	;dh = 0ffh - ch registras
		xor ah, ah
		mov bx, ax
		and byte ptr [Bbufferis + bx], dh
		or byte ptr [Bbufferis + bx], ch
		
endm
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
kodas segment para public 'CODE'

	rasykSimboli proc near
        push ax
        push dx
        
        mov dx, offset simbolis
	mov bx, word ptr outhan
	mov cx, 1
        mov ah, 40h
        int 21h
        
        pop dx
        pop ax
        ret   
  
    rasykSimboli endp
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
    skaityt proc near ;atlikus procedura, perskaitytas baitas laikomas kintamajame "simbolis"
		mov ah, 3fh
		mov bx, word ptr inhan
		mov cx, 0001h
		mov dx, offset simbolis
		int 21h
		cmp ax, 00
		jg toliaudirbt
		call pabaiga
		toliaudirbt:
		ret
	skaityt endp
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
	naujaeil proc near
		push ax
		push dx
		mov ah, 09h
		mov dx, offset neil
		int 21h
		pop dx
		pop ax
		ret
	naujaeil endp
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
	check proc near
	jnc tvarkoj
		mov ah, 09h
		mov dx, offset negerai
		int 21h
		call pabaiga
		tvarkoj:
		mov ah, 09h
		mov dx, offset gerai
		int 21h
		ret
	check endp
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;	
	Adalis proc near 	;dirba su ivestu isvesties failu ir A dalies ivesties failu, perrašo pavadinimo lauką, visus kitus laukus pakeičia didžiausiu jų simboliu.
		pirmaslaukas:	;skaitysiu po viena simboli
			
			call skaityt
			mov ah, 00h
			cmp ax, 00	;ar ne pabaiga
			jne testi
			call pabaiga
			testi:
			mov al, byte ptr simbolis
			call rasyksimboli
			cmp al, ';' ;ar ne lauko galas
			je kitilaukai
			
			jmp pirmaslaukas ;soka, nes pirmas laukas dar nesibaige	
			kitilaukai:
				call skaityt
				cmp ax, 00
				je failopabaiga
				mov al, byte ptr simbolis
				cmp al, '-'	;jei simbolis yra -, reikia ji praleisti
				je minusas
				cmp al, ';'
				je pildomeAts 
				cmp al, 0Ah	;ar al yra nauja eilute?
				je eilutespab	
				cmp al, byte ptr max
				jng nieko
					mov byte ptr max, al
				nieko:
				jmp kitilaukai
				minusas:
				;call rasyksimboli
				jmp kitilaukai
				failopabaiga:	;paraso paskutini simboli ir baigia darba 
				mov al, byte ptr max
				mov byte ptr simbolis, al
				call rasyksimboli
				call pabaiga
				pildomeAts:
					mov al, byte ptr max
					mov byte ptr simbolis, al
					call rasyksimboli
					mov al, ';'
					mov byte ptr simbolis, al
					call rasyksimboli
					mov byte ptr max, 00
					jmp kitilaukai
					
			eilutespab:
				mov al, byte ptr max
				mov byte ptr simbolis, al
				call rasyksimboli
				mov al, 0Ah
				mov byte ptr simbolis, al
				call rasyksimboli
				mov byte ptr max, 00


			jmp pirmaslaukas ;soka, nes baigesi eilute
		ret
	Adalis endp
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
	
	Bdalis proc near
		
		skaitymas:
			mov ah, 3fh
			mov bx, word ptr inhan
			mov cx, 0003h
			mov dx, offset Bbufferis
			int 21h
			jnc okread
			call pabaiga
			okread:
			push ax
			cmp al, 00
			jnz ok
			call pabaiga
			ok:
			;atlieku veiksmus su Bbufferiu ir spausdinu jo pakeista versija
			idek 0008, 0001
			idek 0013h, 0008
			idek 0009, 0013h
			idek 0002, 0009
			pop ax
			mov dx, offset Bbufferis	;rasymas
			mov bx, word ptr outhan
			mov cx, ax
			mov ah, 40h
			int 21h
			
		jmp skaitymas
			
		ret
	Bdalis endp
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
	Cdalis proc near
		skaitymasc:
			mov ah, 3fh	;pirmas baitas
			mov bx, word ptr inhan
			mov cx, 0001h
			mov dx, offset simbolis
			int 21h
			
			cmp al, 00
			jg toliauc
			call pabaiga
			toliauc:
			
			
			
		ret
	Cdalis endp

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
	pradzia:
		mov ax,     seg duomenys                  
		mov ds,     ax
		
		mov cx, es:[0080h]
		cmp cx, 0
		jne okk
		call pabaiga
		okk:
		mov ah, es:[82h]
		mov byte ptr [uzd], ah
		
		sub cx, 3
		mov si, 0
		
		parainput:
			mov ah, es:[si+84h]
			mov byte ptr[input+si], ah
			inc si
			mov ah, es:[si+84h]
			cmp ah, 0dh
			jne toliau100
			mov cx, 1
			toliau100:
		loop parainput
		
		mov ah, 09h
		mov dx, offset intro
		int 21h
		
		mov ax, 3d00h
		mov dx, offset input
		int 21h
		mov word ptr inhan, ax
		call check	;ivesties faila atidarau
		
		mov ah, 0Ah
		mov dx, offset fout
		int 21h

		call naujaeil
		xor bh, bh
		mov bl,     byte ptr [fout + 1] ; bl<-kiek buvo ivesta simboliu
		mov byte ptr [bx + 2 + fout], 00h ; LF + '$' -> eilutes galas
		
		mov ax, 3c00h	;atidarau isvesties faila
		xor cx, cx
		mov dx, offset fout + 2
		int 21h
		mov word ptr outhan, ax
		call check
		xor bh, bh
		mov al, byte ptr [uzd]
		cmp al, 'A'
		jne arB
		call Adalis
		jmp pabaiga
		arB:
		cmp al, 'B'
		jne arC
		call Bdalis
		jmp pabaiga
		arc:
		cmp al, 'C'
		jne ankstyvapabaiga
		call Cdalis
		
		
		
		pabaiga:
		mov ah, 3eh
		mov bx, offset inhan
		int 21h
		ankstyvapabaiga:
		mov ah, 3eh
		mov bx, offset outhan
		int 21h
		
		
		mov ah,     4ch                            ; baigimo funkcijos numeris
		int 21h
	
kodas ends
	end pradzia
	
