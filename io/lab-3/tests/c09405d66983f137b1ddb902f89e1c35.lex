KR_INT 15 int
IDN 15 printf
L_ZAGRADA 15 (
KR_CONST 15 const
KR_CHAR 15 char
IDN 15 format
L_UGL_ZAGRADA 15 [
D_UGL_ZAGRADA 15 ]
D_ZAGRADA 15 )
L_VIT_ZAGRADA 15 {
KR_IF 16 if
L_ZAGRADA 16 (
BROJ 16 1
OP_EQ 16 ==
BROJ 16 2
D_ZAGRADA 16 )
KR_INT 16 int
IDN 16 a
OP_PRIDRUZI 16 =
BROJ 16 2
TOCKAZAREZ 16 ;
KR_ELSE 16 else
KR_INT 16 int
IDN 16 b
OP_PRIDRUZI 16 =
BROJ 16 3
TOCKAZAREZ 16 ;
KR_CONST 17 const
KR_INT 17 int
IDN 17 a
OP_PRIDRUZI 17 =
BROJ 17 1
TOCKAZAREZ 17 ;
KR_RETURN 18 return
BROJ 18 0
TOCKAZAREZ 18 ;
D_VIT_ZAGRADA 19 }
KR_INT 21 int
IDN 21 main
L_ZAGRADA 21 (
KR_VOID 21 void
D_ZAGRADA 21 )
L_VIT_ZAGRADA 21 {
KR_RETURN 22 return
IDN 22 printf
L_ZAGRADA 22 (
NIZ_ZNAKOVA 22 "hello world!\n"
D_ZAGRADA 22 )
TOCKAZAREZ 22 ;
D_VIT_ZAGRADA 23 }