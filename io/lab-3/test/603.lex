KR_INT 17 int
IDN 17 fact
L_ZAGRADA 17 (
KR_INT 17 int
IDN 17 n
D_ZAGRADA 17 )
L_VIT_ZAGRADA 17 {
KR_IF 18 if
L_ZAGRADA 18 (
IDN 18 n
OP_GT 18 >
BROJ 18 0
D_ZAGRADA 18 )
L_VIT_ZAGRADA 18 {
KR_RETURN 19 return
IDN 19 n
OP_PUTA 19 *
IDN 19 fact
L_ZAGRADA 19 (
IDN 19 n
MINUS 19 -
BROJ 19 1
D_ZAGRADA 19 )
TOCKAZAREZ 19 ;
D_VIT_ZAGRADA 20 }
KR_ELSE 20 else
L_VIT_ZAGRADA 20 {
KR_RETURN 21 return
BROJ 21 1
TOCKAZAREZ 21 ;
D_VIT_ZAGRADA 22 }
D_VIT_ZAGRADA 23 }
