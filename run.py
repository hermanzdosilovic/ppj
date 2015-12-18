import os, sys, time, subprocess
from subprocess import Popen, PIPE, STDOUT, DEVNULL

testDir = './io/lab-3/tests/'

def execute(tests):

    execution, correct, N = 0, 0, 0
    for test in list(filter(lambda x: os.path.isfile(tests+x) and x.endswith('in'), os.listdir(tests))):
        N += 1
        imeTest = test[0:len(test)-2]
        runString = sys.argv[1] + ' < ' + testDir + test + ' > ' + testDir + '/' + imeTest + 'u'
        print('Test: ' + test);
        execution = time.time()
        subprocess.call(runString, shell=True, stdin=PIPE, stdout=DEVNULL, stderr=STDOUT)
        execution = round(time.time() - execution, 3)

        with open(testDir + imeTest + 'out', 'U') as myfile:
            ocekivano = myfile.read()
        with open(testDir + imeTest + 'u', 'U') as myfile:
            dobiveno = myfile.read()

        ok = ocekivano == dobiveno

        if ok: correct += 1
        print('OK' if ok else 'WA')
        print(execution)
    print('\nStats : {0}/{1} | {2}%'.format(correct, N, round(correct * 100 / N, 2)))

execute(testDir)
