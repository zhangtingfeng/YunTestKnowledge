from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity
import json


# base_document = "经过几十年的测试"
# documentsAll = "这是自1972年以来出现的另一种成熟算法,经过几十年的测试，看例例子,它是Elasticsearch的默认搜索实现,如果当前对象是.如果当前对象是一个字符串str时,当然，如果你觉得上面的功能还不能满足你，你想使用通配符来查找字符串？没问题！fnmatch这个库就能满足你的要求，看例子！"

def process_tfidf_similarity(base_document, documentsAll):
    vectorizer = TfidfVectorizer()
    documents = [documentsAll]
    # 要生成统一的向量，首先需要将两个文档合并。
    documents.insert(0, base_document)
    embeddings = vectorizer.fit_transform(documents)

    cosine_similarities = cosine_similarity(embeddings[0:1], embeddings[1:]).flatten()

    highest_score = 0
    highest_score_index = 0
    for i, score in enumerate(cosine_similarities):
        if highest_score < score:
            highest_score = score
            highest_score_index = i

    # python2json = json.dumps(a)
    # most_similar_document = documents[highest_score_index]
    # a = {'process_tfidf_similarity': most_similar_document, 'pos': highest_score}

    # print("Most similar document by TF-IDF with the score:", most_similar_document, highest_score)
    return highest_score


def process_indexIF(base_document, documentsAll):
    # strInfdex = documentsAll[documentsAll.find(base_document):documentsAll.find(base_document) + len(base_document)]
    intIndex = documentsAll.find(base_document)
    # a = {'process_indexIF': strInfdex, 'pos': intIndex}
    return intIndex
    # documentsAll.find(base_document)+len(base_document)-1)
