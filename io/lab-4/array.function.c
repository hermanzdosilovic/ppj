void f(int a[]) {
  a[0] = 50;
  return;
}

int main(void) {
  int a[5];
  f(a);
  a[1] = 20;
  return a[0] + a[1];
}
