#!/bin/bash

echo "" > failed_tests.txt
for d in io/lab-4/official/*; do
    if [ ! -d $d ]; then
	continue
    fi
    cinput="$d/test.c"
    frisc="$d/test.frisc"
    out="$d/test.out"
    userout="$d/test.user.out"

    ./ppjc $cinput 2> $frisc && ./frisc.sh $frisc &> $userout
    echo "--- $d ---"
    cmp -s "$out" "$userout"
    if [ $? -eq 1 ]; then
	echo "WA"
	echo $d >> failed_tests.txt
    else
        echo "AC"
        rm "$userout"
    fi
done
