int x[2] = {0, 1};
int len = 2;

int fib(int n){
	if ( n<0 ) return 0;
	if ( n>=len ){
		fib(n-1);
		x[n] = x[n-1] + x[n-2];
		len = n;
	}
	return x[n];
}

int dok(int n){
	int s=0;
	while (--n) s = s+n;
	return s/2;
}

int dok2(int n){
	int s=0;
	for(;;) {
		if (!n) break;
		s=s+--n;
	}
	return s/2;
}

int log(int n){
	if (!n || n==5 && 4==4 || 3<2) return n;
	return -1;
}

int test(int n){
	int a[6];
	a[5] = 5;
	return a[a[a[a[a[5]]]]];
}

int aaa(int n){
	if (n==2) return -1;
	else if (!n) return -1;
	else if (n-5) return -1;
	else if (~~0) return -1;
	return n;
}

int main(void){
	 int f = fib(5); // 5
	 int d = dok(5); // 5
	 int t = dok2(5); // 5
	 int l = log(5); // 5
	 int x = test(5); // 5
	 int a = aaa(5); // 5
	  return f + d + t + l + x + a; // 30 = 0x1E
}
