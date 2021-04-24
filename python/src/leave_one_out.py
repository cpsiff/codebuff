#
# AUTO-GENERATED FILE. DO NOT EDIT
# CodeBuff 1.5.1 'Thu Apr 22 19:48:36 CDT 2021'
#
import numpy as np
import pylab
import matplotlib.pyplot as plt

java_st_err = [0.0, 0.062893085, 0.013661202, 0.028037382, 0.024449877, 0.12195122, 0.04506699, 0.06347439, 0.11627907, 0.01898734, 0.183, 0.07968128, 0.034246575, 0.05207329, 0.059854016, 0.026634382, 0.028478438, 0.05756014, 0.026402641, 0.0091743115, 0.027777778, 0.054545455, 0.061946902, 0.05263158, 0.052200615, 0.10998878, 0.029411765, 0.032, 0.13064133, 0.09023179, 0.023041476, 0.009433962, 0.07917059, 0.024096385, 0.05376344, 0.040625, 0.05871212, 0.1641791, 0.046296295, 0.1641791, 0.05352798, 0.2054054, 0.028037382, 0.07159353, 0.036842104, 0.0, 0.0, 0.053982303, 0.018867925, 0.015873017, 0.23655914, 0.09917355, 0.15625, 0.0914787, 0.053435113, 0.05780822, 0.04668838, 0.0453125, 0.05963397]
sylte_1_err = [0.08713693, 0.05102041, 0.07329843, 0.043956045, 0.09701493, 0.038674034, 0.13821138, 0.031145718, 0.08495019, 0.03902715]
style_2_err = [0.08571429, 0.055276383, 0.018867925, 0.095115684, 0.094545454, 0.04644809, 0.13709678, 0.032467533, 0.076331966, 0.037995595]
style_google_err = [0.08713693, 0.05102041, 0.07329843, 0.024725275, 0.10261194, 0.0359116, 0.13821138, 0.031145718, 0.07970635, 0.03902715]

language_data = [java_st_err, sylte_1_err, style_2_err, style_google_err]
labels = ["String Template\nn=59", "Our Style 1\nn=10", "Our Style 2\nn=10", "Our Style Google\nn=10"]
fig = plt.figure()
ax = plt.subplot(111)
ax.boxplot(language_data,
           whis=[10, 90], # 10 and 90 % whiskers
           widths=.35,
           labels=labels,
           showfliers=False)
ax.set_xticklabels(labels, rotation=60, fontsize=10)
ax.tick_params(axis='both', which='major', labelsize=10)
plt.xticks(range(1,len(labels)+1), labels, rotation=60, fontsize=10)
pylab.ylim([0,.15])
ax.yaxis.grid(True, linestyle='-', which='major', color='lightgrey', alpha=0.5)
ax.set_xlabel("Grammar and corpus size", fontsize=12)
ax.set_ylabel("Misclassification Error Rate", fontsize=12)
ax.set_title("Leave-one-out Validation Using Error Rate\nBetween Formatted and Original File")
plt.tight_layout()
fig.savefig('images/leave_one_out.pdf', format='pdf')
plt.show()
