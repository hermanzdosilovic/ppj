int f(int a, int b) {
  return a - b;
}

int main(void) {
  int x = 300;
  int y = 200;
  return f(x + 100, y + 100); // 100
}
