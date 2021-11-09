from wand.image import Image

filename = "somefile.pdf"

with(Image(filename=filename, resolution=120)) as source:
    images = source.sequence
    pages = len(images)
    for i in range(pages):
        n = i + 1
        newfilename = filename[:-4] + str(n) + '.jpeg'
        Image(images[i]).save(filename=newfilename)
