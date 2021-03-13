from scipy.stats import lognorm
import math

def log_normal(x:float,mu=0,sigma=1):
    if(x == 0):
        return 0
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

def log_normal_in_interval(mu,sigma,min,max):
    return log_normal(max,mu,sigma) - log_normal(min,mu,sigma)
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

'''
    creates and overwrites csv
'''
def create_csv(fName):
    csvName = fName[:-4]

    csvName = csvName + '_table.csv'
    with open(csvName,'w') as f:
        f.write('Interval,Actual,Expected,Expected Normalized Square of Difference')
        f.close()
    return csvName

def add_to_table(csvName,interval,expected,actual,dif):
    with open(csvName,'a') as f:
        f.write('\n'+str(interval)+','+str(actual)+','+str(expected)+','+str(dif))
        f.close()

def write_sum_to_end(csvName,sum):
    with open(csvName,'a') as f:
        f.write('\n\n\nSum,'+str(sum))
        f.close()

fName = input('file name: ')
li = read_csv(fName)
avg = float(input('Mu of distribution: '))
sigma = float(input('Sigma of distribution: '))
largest = get_largest(li)
interval = largest / 25
csvName = create_csv(fName)
current = 0
sum_of_differences = 0
next_val = current + interval
while True:
    expected = log_normal_in_interval(avg,sigma,current,next_val)
    actual = density_in_interval(li,current,next_val)
    sum_of_differences = sum_of_differences + (pow(actual - expected,2)/expected)
    add_to_table(csvName,next_val,expected,actual,(pow(actual - expected,2)/expected))
    current = next_val
    next_val = next_val + interval
    if next_val > largest:
        break

write_sum_to_end(csvName,sum_of_differences)

print('Degrees of Freedom: '+str(24))
print('Sum of chi: '+str(sum_of_differences))
print('Reject Hyptothisis? ' + str(sum_of_differences > 36.4))

