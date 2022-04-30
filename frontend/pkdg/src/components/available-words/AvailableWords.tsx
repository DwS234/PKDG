import { useEffect, useState } from "react";
import User from "../../model/User";
import Word from "../../model/Word";
import RepetitionsService from "../../services/repetitions/RepetitionsService";
import WordsService from "../../services/words/WordsService";
import AvailableWord from "../available-word/AvailableWord";
import Pagination from "../pagination/Pagination";

const AVAILABLE_WORDS_PER_PAGE: number = 10;

interface AvailableWordsProps {
  user: User | null;
}

const AvailableWords = ({ user }: AvailableWordsProps) => {
  const [availableWords, setAvailableWords] = useState<Array<Word>>([]);
  const [currentPage, setCurrentPage] = useState(1);

  const indexOfLastAvailableWord = currentPage * AVAILABLE_WORDS_PER_PAGE;
  const indexOfFirstAvailableWord = indexOfLastAvailableWord - AVAILABLE_WORDS_PER_PAGE;
  const currentAvailableWords = availableWords.slice(indexOfFirstAvailableWord, indexOfLastAvailableWord);

  if (currentAvailableWords.length === 0 && currentPage > 1) {
    setCurrentPage(currentPage - 1);
  }

  useEffect(() => {
    const getWordsToAddByUser = async () => {
      try {
        const availableWordsToRepeat = await WordsService.getAvailableWordsToRepeat(user!.username);
        setAvailableWords(availableWordsToRepeat);
      } catch (e) {
        console.log(e);
      }
    };
    getWordsToAddByUser();
  }, []);

  const addRepetition = async (wordId: string) => {
    try {
      await RepetitionsService.addRepetition(wordId, user!.username);
      setAvailableWords(availableWords.filter((availableWord) => availableWord.wordId !== wordId));
    } catch (e) {
      console.log(e);
    }
  };

  const paginate = (pageNumber: number) => setCurrentPage(pageNumber);

  return (
    <>
      <div className="mt-5 flex justify-center">
        <a className="block min-w-[50%] p-6 pl-10 pr-10 max-w-[90%] max-h-[80%] bg-white rounded-lg border border-gray-200 shadow-md dark:bg-gray-800 dark:border-gray-700 dark:hover:bg-gray-700">
          <h5 className="mb-2 text-2xl font-bold tracking-tight text-gray-900 dark:text-white">
            Lista słów możliwych do dodania do powtórek
          </h5>
          {currentAvailableWords.map((availableWord) => (
            <AvailableWord key={availableWord.wordId} word={availableWord} onAddHandler={addRepetition} />
          ))}
          {currentAvailableWords.length === 0 && <span>Brak słów możliwych do dodania do powtórek</span>}
          <Pagination
            currentPage={currentPage}
            itemsPerPage={AVAILABLE_WORDS_PER_PAGE}
            totalItemCount={availableWords.length}
            paginate={paginate}
          />
        </a>
      </div>
    </>
  );
};

export default AvailableWords;
