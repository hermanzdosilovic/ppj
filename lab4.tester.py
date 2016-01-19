#!/usr/bin/env python

import os, sys, time, subprocess
from subprocess import Popen, PIPE, STDOUT

testDir = os.path.sep.join(('io', 'lab-4' + os.path.sep))

def execute(tests):
    execution, correct, N = 0, 0, 1
    failedExamples = open('failed_tests.txt', 'w')

    for test in list(filter(lambda x: os.path.isfile(tests+x) and x.endswith('.c'), os.listdir(tests))):
        testName = test[0:len(test) - 1]

	friscFile = tests + testName + 'frisc'
	inputFile = tests + test
	actualOutputFile = tests + testName + 'user.out'
	expectedOutputFile = tests + testName + 'out'

	runString = 'bash -c \"./ppjc ' + inputFile + ' 2> ' + friscFile + ' && ./frisc.sh ' + friscFile + ' &> ' + actualOutputFile + "\""
        print '--- ' + test + ' ---'

        execution = time.time()
        subprocess.call(runString, shell=True, stdin=PIPE, stdout=open(os.devnull, "w"), stderr=STDOUT)
        execution = round(time.time() - execution, 3)

        with open(expectedOutputFile, 'U') as myfile:
            expectedOutput = myfile.read()
            myfile.close()
        with open(actualOutputFile, 'U') as myfile:
            actualOutput = myfile.read()
            myfile.close()
        ok = expectedOutput == actualOutput

        if ok: 
            correct += 1
            os.remove(actualOutputFile)
	    print 'AC ',
        else: 
            failedExamples.write(tests + test + '\n')
	    print 'WA ',

        print str(execution) + '\n'
        N += 1
    
    N -= 1 
    failedExamples.close()
    if correct == N:
    	os.remove('failed_tests.txt')

    print '\nStats : {0}/{1}'.format(correct, N)

if len(sys.argv) > 1 and sys.argv[1] == "--build":
    subprocess.call('bash -c \"./GEN --build-only\"', shell=True)
execute(testDir)
