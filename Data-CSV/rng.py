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




for x in range(300):
    rand = random()

    for i in np.arange(1, 1000, 0.001):
        prob = log_normal(i,1.15,1.3)
        if prob > rand:
            write_to_csv(i)
            break



