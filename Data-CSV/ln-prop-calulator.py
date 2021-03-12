import math


while True:
    fName = input('Enter File Name:')
    with open(fName,'r') as f:
        lines = f.read()
        f.close()


    values = lines.splitlines()

    sum = 0

    for val in values[1:-1]:
        sum = sum + math.log(float(val))

    avg = sum / (len(values) - 2)

    sum_of_squares = 0
    for val in values[1:-1]:
        sum_of_squares = sum + pow((float(val) - avg),2)

    sigma = sum_of_squares / (len(values) - 1)

    with open('log-normal-properties.txt','a') as f:
        f.write(fName+'\n')
        f.write("\tMu: "+str(avg)+'\n')
        f.write("\tSigma: "+str(sigma)+'\n')
    print("Mu: "+str(avg))
    print("Sigma: "+str(sigma))