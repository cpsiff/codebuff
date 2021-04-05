#
# AUTO-GENERATED FILE. DO NOT EDIT
# CodeBuff 1.5.1 'Tue Mar 30 17:22:39 CDT 2021'
#
import numpy as np
import matplotlib.pyplot as plt

fig = plt.figure()
ax = plt.subplot(111)
N = 30
sizes = range(1,N+1)
java_custom = [0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0]
ax.plot(range(1,len(java_custom)+1), java_custom, label="java_custom", marker='', color='')

ax.yaxis.grid(True, linestyle='-', which='major', color='lightgrey', alpha=0.5)
ax.set_xlabel("Number of training files in sample corpus subset", fontsize=14)
ax.set_ylabel("Median Error rate for 50 trials", fontsize=14)
ax.set_title("Effect of Corpus size on Median Leave-one-out Validation Error Rate")
plt.legend()
plt.tight_layout()
fig.savefig('images/subset_validator.pdf', format='pdf')
plt.show()
