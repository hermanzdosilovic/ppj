KR_INT 1 int
IDN 1 y
TOCKAZAREZ 1 ;
KR_INT 2 int
IDN 2 x
TOCKAZAREZ 2 ;
KR_INT 4 int
IDN 4 zbrajanje
L_ZAGRADA 4 (
KR_VOID 4 void
D_ZAGRADA 4 )
L_VIT_ZAGRADA 4 {
KR_INT 5 int
IDN 5 x
OP_PRIDRUZI 5 =
BROJ 5 3
TOCKAZAREZ 5 ;
KR_INT 6 int
IDN 6 y
OP_PRIDRUZI 6 =
BROJ 6 4
TOCKAZAREZ 6 ;
KR_RETURN 7 return
IDN 7 x
PLUS 7 +
IDN 7 y
TOCKAZAREZ 7 ;
D_VIT_ZAGRADA 8 }
KR_INT 10 int
IDN 10 main
L_ZAGRADA 10 (
KR_VOID 10 void
D_ZAGRADA 10 )
L_VIT_ZAGRADA 10 {
KR_INT 11 int
IDN 11 z
TOCKAZAREZ 11 ;
IDN 12 z
OP_PRIDRUZI 12 =
IDN 12 zbrajanje
L_ZAGRADA 12 (
D_ZAGRADA 12 )
TOCKAZAREZ 12 ;
KR_RETURN 14 return
BROJ 14 0
TOCKAZAREZ 14 ;
D_VIT_ZAGRADA 15 }
