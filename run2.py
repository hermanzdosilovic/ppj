import sys
import time
import subprocess
from subprocess import PIPE, STDOUT, DEVNULL

testDir = './io/lab-3/tests/'


def execute(tests):
    execution, correct, N = 0, 0, 0
    krivi = []
#    with open("krpa_in.txt") as in_list:
#        in_file = in_list.read().splitlines()

    with open("truba_in.txt") as in_list:
        in_file = in_list.read().splitlines()
    for test in in_file:
        N += 1
        runString = './' + sys.argv[1] + ' < ' + testDir + test + 'in' + ' > ' + testDir + test + 'u'
        print('Test: ' + test + ' no: ' + str(N))
        execution = time.time()
        subprocess.call(runString, shell=True, stdin=PIPE, stdout=DEVNULL, stderr=STDOUT)
        execution = round(time.time() - execution, 3)

        with open(testDir + test + 'out', 'U') as myfile:
            ocekivano = myfile.read()
        with open(testDir + test + 'u', 'U') as myfile:
            dobiveno = myfile.read()

        ok = ocekivano == dobiveno

        if ok:
            correct += 1
        else:
            krivi.append(test)
        print('OK' if ok else 'WA')
        print(execution)

    kriviFile = open('krivi', 'w')
    for kt in krivi:
        kriviFile.write("%s\n" % kt)
    print('\nStats : {0}/{1} | {2}%'.format(correct, N, round(correct * 100 / N, 2)))
    kriviFile.close()
    in_list.close()

execute(testDir)
