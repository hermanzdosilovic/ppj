#!/usr/bin/env python

import os, sys, time, subprocess
from subprocess import Popen, PIPE, STDOUT

testDir = sys.argv[1]
inputExtension = sys.argv[2]

def execute(tests):
    execution, correct, N = 0, 0, 1
    failedExamples = open('failed_tests.txt', 'w')

    for test in list(filter(lambda x: os.path.isfile(tests+x) and x.endswith('.' + inputExtension), os.listdir(tests))):
        testName = test[0:len(test) - 2]
        runString = "bash -c ./" + sys.argv[3] + ' < ' + tests + test + ' > ' + tests + testName + 'user.out' 
        print(str(N) + ':\t' + test),

        execution = time.time()
        subprocess.call(runString, shell=True, stdin=PIPE, stdout=open(os.devnull, "w"), stderr=STDOUT)
        execution = round(time.time() - execution, 3)

        with open(tests + testName + 'out', 'U') as myfile:
            expectedOutput = myfile.read()
            myfile.close()
        with open(tests + testName + 'user.out', 'U') as myfile:
            actualOutput = myfile.read()
            myfile.close()
        ok = expectedOutput == actualOutput

        if ok: 
            correct += 1
            os.remove(tests + testName + 'user.out')
	    print ' AC ',
        else: 
            failedExamples.write(tests + test + '\n')
	    print ' WA ',

        print(execution)
        N += 1
    
    N -= 1 
    failedExamples.close()
    if correct == N:
    	os.remove('failed_tests.txt')

    print '\nStats : {0}/{1}'.format(correct, N)

execute(testDir)
