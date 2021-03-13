from scipy.stats import lognorm
import math

def log_normal(x:float,mu=0,sigma=1):
    sigma = math.sqrt(sigma)
    a = (math.log(x) - mu)/math.sqrt(2*sigma**2)
    p = 0.5 + 0.5*math.erf(a)
    return p

def read_csv(fName):
    ln = None
    with open(fName,'r') as f:
        ln = f.read()
        f.close()
    return ln.splitlines()[1:]

'''
    Get the largest number in the data set
'''
def get_largest(ln):
    largest = float(ln[0])
    for i in ln:
        current = float(i)
        if current > largest:
            largest = float(i)

    return largest

'''
    Calculates the actual value from the given data
'''
def density_in_interval(ln, min, max):
    count = 0

    for i in ln:
        if float(i) > min and float(i) <= max:
            count = count + 1
    
    return count/len(ln)


li = read_csv(input('file name: '))
avg = float(input('Mu of distribution: '))
sigma = float(input('Sigma of distribution: '))
largest = get_largest(li)
interval = largest / 25

current = 0
sum_of_differences = 0
next_val = current + interval
while True:
    expected = log_normal(next_val,avg,sigma)
    actual = density_in_interval(li,current,next_val)
    sum_of_differences = sum_of_differences + (pow(actual - expected,2)/expected)
    current = next_val
    next_val = next_val + interval
    if next_val > largest:
        break
print('Degrees of Freedom: '+str(24))
print('Sum of chi: '+str(sum_of_differences))
print('Reject Hyptothisis? ' + str(sum_of_differences > 36.4))

