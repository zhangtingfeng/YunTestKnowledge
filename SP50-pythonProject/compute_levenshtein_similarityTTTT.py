# Using difflib is python 3.6 default package
import difflib
import re
from simhash import Simhash

def compute_levenshtein_distance(sentence1: str, sentence2: str) -> int:
    """
    compute levenshtein distance.

    """
    leven_cost = 0
    s = difflib.SequenceMatcher(None, sentence1, sentence2)
    for tag, i1, i2, j1, j2 in s.get_opcodes():

        if tag == 'replace':
            leven_cost += max(i2 - i1, j2 - j1)
        elif tag == 'insert':
            leven_cost += (j2 - j1)
        elif tag == 'delete':
            leven_cost += (i2 - i1)

    return leven_cost



def compute_levenshtein_similarity(sentence1: str, sentence2: str) -> float:
    """Compute the hamming similarity."""
    leven_cost = compute_levenshtein_distance(sentence1, sentence2)
    return 1 - (leven_cost / len(sentence2))



def compute_simhash_hamming_similarity(sentence1: str, sentence2: str) -> float:
    """need to normalize after compute!"""

    def get_features(s):
        width = 3
        s = s.lower()
        s = re.sub(r'[^\w]+', '', s)
        return [s[i:i + width] for i in range(max(len(s) - width + 1, 1))]

    hash_value1 = Simhash(get_features(sentence1)).value
    hash_value2 = Simhash(get_features(sentence2)).value

    return compute_levenshtein_similarity(str(hash_value1), str(hash_value2))


def compute_jaccard_similarity(sentence1: str, sentence2: str) -> float:
    word_set1 = set(sentence1.strip(" ").split(" "))
    word_set2 = set(sentence2.strip(" ").split(" "))

    return len(word_set1 & word_set2) / len(word_set1 | word_set2)

