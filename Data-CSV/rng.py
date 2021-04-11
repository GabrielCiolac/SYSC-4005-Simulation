import math
from random import random
import numpy as np

'''
cdf log normal
'''
def log_normal(x:float,mu=0,sigma=1):
    if(x == 0):
        return 0
    sigma = math.sqrt(sigma)
    a = (math.log(x) - mu)/math.sqrt(2*sigma**2)
    p = 0.5 + 0.5*math.erf(a)
    return p



def write_to_csv(num):
    with open('test.csv','a') as f:
        f.write(str(num)+'\n')
        f.close()

def clear():
    with open('test.csv','w') as f:
        f.write('Test\n')
        f.close()


'''
    Generates a random number on a log_normal distribution

    it starts by generating a uniform random number on the range:

    0 <= x <= 1

    this well represent the target CDF probability

    It will then begin to iterate between 0 and 1000 inclusive in steps of 0.001

    The step being 0.001 is to conform to the sig-fig of this class

    It will take the current step and enter it into a CDF log-normal distribution
    if the probabily of the step is equal to or exceeds the generated number
    the step gets return
'''
def rand_log_normal(mu,sigma):
    rand = random()
    for i in np.arange(0, 1000, 0.001):
        prob = log_normal(i,mu,sigma)
        if prob >= rand:
            return i
        



