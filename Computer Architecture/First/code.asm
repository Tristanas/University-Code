
.model small
       ASSUME CS:kodas, DS:duomenys, SS:stekas
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
stekas segment word stack 'STACK'
       dw 400h dup (00)               ; stekas -> 2 Kb
stekas ends
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;define išvesti V
duomenys segment para public 'DATA' 
   
    prisistatymas:
       db 'Vilius Minkevicius, 1 programu sistemos kursas, 2 grupe $'
    prasymas:
	db 'Iveskite eilute:',0Dh, 0Ah, '$'
    naujaEilute:   
       db 0Dh, 0Ah, '$'  ; tekstas ant ekrano
    dar_pranesimas:
       db 'Tu ivedei: $'
    rezultato_pranesimas:
       db 'A uzduociai komanda sukeite 4 ir 6 simbolius, o paskutini padare B: $'
    buferisIvedimui:
	db 80, 00, 100 dup ('*')
	Bdalis:
	db 'B uzduotis atspausdina kiekvieno simbolio paskutinio, 3 ir 4 tu bitu sumas $'
	Cdalis:
	db 'C uzduotis spausdina stulpeliu kiekvieno simbolio baito reiksmes, padalintas is 9, ir is ju atemus 10: $'
	Ddalis:
	db 'D uzduotis spausdins stulpeliu numberius baitu, kuriu 8-taineje skaitmenu suma yra tarp 3 ir 5 $'
	Numeriai:
	db '00010203040506070809101112131415161718192021222324252627282930$'
	dtre:		 ;d dalies tarpinio rezultato eilute
	db '00 s 00 T ',0Dh, 0Ah, '$'	; pirma numeris, tada simbolis ir 8-tainiu skaitmenu suma, pabaigai, ar tinka
	;; +012345678
	counter:
	db 0
	drez:		 ;d dalies rezultatas
	db 'Tinkamu simboliu numeriai mazejimo tvarka:',0Dh, 0Ah, '$'
duomenys ends
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
kodas segment para public 'CODE'
    pradzia:

       mov ax,     seg duomenys                   ; "krauname" duomenu segmenta
       mov ds,     ax
       
       mov ah,     09                             ; spausdinimo funkcijos numeris
       mov dx,     offset prisistatymas              ; isvedamojo teksto adresas
       int 21h

       mov ah,     09                             ; spausdinimo funkcijos numeris
       mov dx,     offset naujaEilute             ; isvedamojo teksto adresas
       int 21h

       mov ah,     09                             ; spausdinimo funkcijos numeris
       mov dx,     offset prasymas                ; isvedamojo teksto adresas
       int 21h

       mov ah,     0Ah                            ; ivesties funkcija
       mov dx,     offset buferisIvedimui         ; buferis
       int 21h       

       mov ah,     09                             ; spausdinimo funkcijos numeris
       mov dx,     offset naujaEilute             ; isvedamojo teksto adresas
       int 21h

       
      
       ; Skaityk 0Ah int 21h funkcijos aprasa: 
       ; http://spike.scu.edu.au/~barry/interrupts.html#ah0a
	
       mov bl,     byte ptr [buferisIvedimui + 1] ; bl<-kiek buvo ivesta simboliu
       xor bh,     bh                             ; bh <- 0   
       mov word ptr [bx + 3 + buferisIvedimui], 240Ah ; LF + '$' -> eilutes galas
       
       ; Isvedame ivesta eilute:
       mov ah,     09                             ; spausdinimo funkcijos numeris
       mov dx,     offset dar_pranesimas          ; isvedamojo teksto adresas
       int 21h

       mov ah,     09                             ; spausdinimo funkcijos numeris
       mov dx,     offset naujaEilute             ; isvedamojo teksto adresas
       int 21h

       mov dx,     offset buferisIvedimui + 2
       mov ah,     09
       int 21h
   
	; A dalis
db '00 s 00 T ',0Dh, 0Ah, '$'	; pirma numeris, tada simbolis ir 8-tainiu skaitmenu suma, pabaigai, ar tinka
;; +012345678 
      
; Keiciame eilute (turima omenyje, kad DOS issaugojo bx !):
    
	mov al,     byte ptr[buferisIvedimui + 5]   ; al <- 4 tas simbolis
	xchg byte ptr[buferisIvedimui + 7], al	    ;al <-6 tas ir 6 tas  <- 4 tas
	mov byte ptr[buferisIvedimui + 5], al	    ;4 tas  <- 6 tas
	mov al, "B"
	mov cl, byte ptr[buferisIvedimui + bx + 1]	   ;kopijuoju paskutini nari atstatymui
	mov byte ptr[buferisIvedimui + bx + 1], al  ; paskutinis <- B

       ; Isvedame pakeista eilute:
       mov ah,     09                             ; spausdinimo funkcijos numeris
       mov dx,     offset rezultato_pranesimas    ; isvedamojo teksto adresas
       int 21h

       mov ah,     09                             ; spausdinimo funkcijos numeris
       mov dx,     offset naujaEilute             ; isvedamojo teksto adresas
       int 21h

       mov dx,     offset buferisIvedimui + 2
       mov ah,     09
       int 21h


				;atstatau ivedima
	mov byte ptr[buferisIvedimui + bx + 1], cl ;atstatau paskutini nari
	mov al,     byte ptr[buferisIvedimui + 5]  ;al tampa 6 nariu
	xchg byte ptr[buferisIvedimui + 7], al	   ;6 narys atgauna pradine reiksme
	mov byte ptr[buferisIvedimui + 5], al	   ;4 narys atstatomas
	
				;B dalis
	mov al, 09h
	mov dx, offset Bdalis
	int 21h
	mov ah, 09h
	mov dx, offset naujaEilute
	int 21h
	
	mov cx, bx
	xor bx, bx
	xor dx, dx
	label1:
		mov al, byte ptr[buferisIvedimui+2+bx]
		xor dl, dl
		shr al, 4
		adc dl, 0
		shr al, 1
		adc dl, 0
		shr al, 3
		adc dl, 0
		add dl, "0"
		mov ah, 02h
		int 21h
		mov ah, 02h
		mov dl, " "
		int 21h
		inc bx
	loop label1		;zemyn cx=0000h

				;C dalis
	mov ah, 09h
	mov dx, offset naujaEilute
	int 21h
	mov ah, 09h
	mov dx, offset Cdalis
	int 21h
	mov ah, 09h
	mov dx, offset naujaEilute
	int 21h
	
	mov cl,     byte ptr [buferisIvedimui + 1] ; cl<-kiek buvo ivesta simboliu
        xor ch, ch
	xor bx, bx
label2:
	mov al, byte ptr [buferisIvedimui + 2 + bx] 
	mov ah, 00h
	mov dl, 09h
	div dl
	xor ah, ah
	cmp al, 0ah	;tikrinu, ar al >=10
	jge nemazesnisnei10
	push ax
	mov ah, 02h
	mov dl, "-"
	int 21h
	pop ax
	sub al, 0ah 		;al = -1, -2 ar panasiai
	mov dh, al
	xor al, al
	sub al, dh
	mov dl, 0ah
	mov ah, 00h
	div dl			;ah liekana, al -sveikoji dalis
	mov dl, "0"
	add dl, al
	mov dh, ah
	mov ah, 02h
	int 21h			;spausdinu desimtis
	mov ah, 02h
	mov dl, dh
	add dl, "0"
	int 21h	
		;spausdinu vienetus
	jmp Spausdinti
	
nemazesnisnei10:
	;; al>=10
	sub al, 10
	mov dl, 0ah
	div dl			;ah liekana, al -sveikoji dalis
	mov dl, al
	mov dh, ah
	mov ah, 02h
	add dl, "0"
	int 21h			;spausdinu desimtis
	mov ah, 02h
	mov dl, dh
	add dl, "0"
	int 21h
	
Spausdinti:	
	inc bx
	mov ah, 09h
	mov dx, offset naujaEilute
	int 21h
loop label2

				;D dalis
	mov ah, 09h
	mov dx, offset Ddalis
	int 21h
	mov ah, 09h
	mov dx, offset naujaEilute
	int 21h
	mov cl, byte ptr[buferisIvedimui+1]
	xor bx, bx
	label3:
		mov al, byte ptr[buferisIvedimui+2+bx]	;imu simboli
		mov byte ptr[dtre+3], al		;dedu simboli
		push ax
		shl bx, 1
		mov ax, word ptr[Numeriai+bx]		;imu numeri
		mov word ptr[dtre], ax		;dedu
		shr bx, 1
		pop ax
		xor ah, ah
		xor dx, dx
		mov dl, 08d
		div dl		;imu vienetus
		add dh, ah
		xor ah, ah
		div dl		;imu desimtis
		add dh, ah
		xor ah, ah
		div dl		;imu simtus
		add dh, ah	;dh jau yra 8-tainio skaiciaus skaitmn suma tikrinu ar yra tarp 3 ir 5
		mov dl, "N"
		mov byte ptr[dtre+08h], dl
		mov al, dh	;ikeliu i ah 8tainiu skaitmenu suma
		xor ah, ah
		mov dl, 0ah
		div dl
		add ax, 3030h
		mov byte ptr[dtre+5], al
		mov byte ptr[dtre+6], ah
		xor ah, ah
		cmp dh, 5
		ja didesnisuz5
		cmp dh, 3
		jb mazesnisuz3 	;V spausdinimas, nes jei dh>5 ar dh<3, jis soktu prie inc bx
		mov dl, "T"	;cia dh jau nebereikalingas
		mov byte ptr[dtre+08h], dl
		mov al, dl
		mov dl, 0ah
		div dl
		push bx
		mov al, 01h
		add byte ptr[counter], al
		didesnisuz5:	
		mazesnisuz3:
	mov ah, 09h
	mov dx, offset dtre
	int 21h
	inc bx
	loop label3
	;; steke turiu tinkamu simboliu numerius atvirkstine tvarka
	mov cl, byte ptr[counter]
	xor ch, ch
	xor bx, bx

	mov dx, offset drez
	mov ah, 09h
	int 21h
	as:			;atsakymo spausdinimas
		pop ax
		xor ah, ah
		mov dx, 000ah
		div dl
		mov dx, 3030h
		add dl, al
		add dh, ah
		mov ah, 02h
		int 21h
		mov dl, dh
		mov ah, 02h
		int 21h
		mov dx, offset naujaEilute
		mov ah, 09h
		int 21h
		dec bx
	loop as
	       mov ah,     4ch                            ; baigimo funkcijos numeris
	int 21h
kodas  ends
    end pradzia 
