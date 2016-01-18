import os, sys, time, subprocess
from subprocess import Popen, PIPE, STDOUT

testDir = os.path.sep.join(('io', 'lab-3', 'tests' + os.path.sep))

def execute(tests):
    execution, correct, N = 0, 0, 1
    kriviFile = open('failed_tests.txt', 'w')
    for test in list(filter(lambda x: os.path.isfile(tests+x) and x.endswith('.in'), os.listdir(tests))):
        imeTest = test[0:len(test)-2]
        runString = "./" + "SEM" + ' < ' + tests + test + ' > ' + tests + imeTest + 'user.out' 
	runString = "bash -c " + runString
        print(str(N) + ': ' + test),
        execution = time.time()
        subprocess.call(runString, shell=True, stdin=PIPE, stdout=open(os.devnull, "w"), stderr=STDOUT)
        execution = round(time.time() - execution, 3)

        with open(tests + imeTest + 'out', 'U') as myfile:
            ocekivano = myfile.read()
            myfile.close()
        with open(tests + imeTest + 'user.out', 'U') as myfile:
            dobiveno = myfile.read()
            myfile.close()
        ok = ocekivano == dobiveno

        if ok: 
            correct += 1
            os.remove(tests + imeTest + 'user.out')
        else: 
            kriviFile.write(tests + test + '\n')
        print(' OK ' if ok else ' WA '),
        print(execution)
        N += 1
    
    kriviFile.close()
    if correct == N - 1:
    	os.remove('failed_tests.txt')

    print('\nStats : {0}/{1} | {2}%'.format(correct, N, round(correct * 100 / N, 2)))

subprocess.call("bash -c \"./SEM --build-only\"", shell=True)
execute(testDir)
