'''
Script converts data from newline seperated values to comma seperated values
'''

fName = input('Enter File Name:')
with open(fName,'r') as f:
    lines = f.read()
    f.close()

values = lines.splitlines()
s = ''

for val in values:
    s = s + val.strip() + ','

s = s[:-2] #removes final coma
print(s)

with open(fName,'w') as f:
    f.write(s)
    f.close()

print('Done')
