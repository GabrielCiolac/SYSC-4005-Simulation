#!/usr/bin/env python
# coding: utf-8

# In[47]:


import matplotlib.pyplot as plt
import numpy as np
import pandas as pd
import seaborn as sns
import math 
from scipy import stats
get_ipython().run_line_magic('matplotlib', 'inline')
import statsmodels.api as sm
import scipy.stats as stats


# In[227]:


servinsp1 = pd.read_csv('servinsp1.csv')
servinsp1_array = np.asarray(servinsp1['time'])
servinsp22 = pd.read_csv('servinsp22.csv')
servinsp22_array = np.asarray(servinsp22['time'])
servinsp23 = pd.read_csv('servinsp23.csv')
servinsp23_array = np.asarray(servinsp23['time'])
ws1 = pd.read_csv('ws1.csv')
ws1_array = np.asarray(ws1['time'])
ws2 = pd.read_csv('ws2.csv')
ws2_array = np.asarray(ws2['time'])
ws3 = pd.read_csv('ws3.csv')
ws3_array = np.asarray(ws3['time'])

data = [servinsp1_array,servinsp22_array,servinsp23_array,ws1_array,ws2_array,ws3_array]


# In[228]:


for data_values in data:
    data_values.sort()


# In[229]:


##not used the bin size will be 25
bin_count = math.sqrt(servinsp1_array.size)
bin_count_min = math.ceil(bin_count)
bin_count_max =servinsp1_array.size/5
bin_count_max = math.ceil(bin_count_max)
bin_size = 25


# In[231]:


##LogNormal dist
for data_values in data:
    sns.displot(data_values,bins =  bin_size,kde=True)


# In[232]:


values_to_be_inversed = []
for x in range(1,301) :
    values_to_be_inversed.append((x-0.5)/300)
    


# In[236]:


inversed_values = stats.lognorm(1, scale=np.exp(1.5)).ppf(values_to_be_inversed)
plt.scatter(inversed_values,data[0], c='r')
plt.axline([0, 0], [1, 1])
plt.xlabel('Inverse Lognormal values')
plt.ylabel('Ordered values')
plt.title('servinsp1 QQ plot with lognormal µ = 1 , σ^2 = 1.5')


# In[247]:


inversed_values = stats.lognorm(1, scale=np.exp(1.9)).ppf(values_to_be_inversed)
plt.scatter(inversed_values,data[1], c='r')
plt.axline([0, 0], [1, 1])
plt.xlabel('Inverse Lognormal values')
plt.ylabel('Ordered values')
plt.title('servinsp22 QQ plot with lognormal µ = 1 , σ^2 = 1.9')


# In[266]:


inversed_values = stats.lognorm(1, scale=np.exp(2.3)).ppf(values_to_be_inversed)
plt.scatter(inversed_values,data[2], c='r')
plt.axline([0, 0], [1, 1])
plt.xlabel('Inverse Lognormal values')
plt.ylabel('Ordered values')
plt.title('servinsp23 QQ plot with lognormal µ = 1 , σ^2 = 2.3')


# In[275]:


inversed_values = stats.lognorm(1, scale=np.exp(0.9)).ppf(values_to_be_inversed)
plt.scatter(inversed_values,data[3], c='r')
plt.axline([0, 0], [1, 1])
plt.xlabel('Inverse Lognormal values')
plt.ylabel('Ordered values')
plt.title('Workstation 1 QQ plot with lognormal µ = 1 , σ^2 = 0.9')


# In[280]:


inversed_values = stats.lognorm(1.1, scale=np.exp(1.8)).ppf(values_to_be_inversed)
plt.scatter(inversed_values,data[4], c='r')
plt.axline([0, 0], [1, 1])
plt.xlabel('Inverse Lognormal values')
plt.ylabel('Ordered values')
plt.title('Workstation 2 QQ plot with lognormal')


# In[284]:


inversed_values = stats.lognorm(1, scale=np.exp(1.5)).ppf(values_to_be_inversed)
plt.scatter(inversed_values,data[5], c='r')
plt.axline([0, 0], [1, 1])
plt.xlabel('Inverse Lognormal values')
plt.ylabel('Ordered values')
plt.title('Workstation 3 QQ plot with lognormal')


# In[ ]:




