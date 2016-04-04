sid = 1
f = open('transformed.seq','w+')
for line in open('msnbc990928.seq'):
	items = line.strip().split(' ')
	if len(items) == 1:
		continue
	for tid in range(len(items)):
		f.write(str(sid) + " " + str(tid) + " 1 " + items[tid] + "\n")
	sid = sid + 1
