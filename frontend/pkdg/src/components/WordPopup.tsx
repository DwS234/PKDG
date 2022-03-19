import { useState } from "react";
import { useEffect } from "react";
import { connect } from "react-redux";
import Word from "../model/Word";
import WordInRepetition from "../model/WordInRepetition";
import { RootState } from "../redux/reducers";
import RepetitionsService from "../services/repetitions/RepetitionsService";

interface WordPopupProps {
  open: boolean;
  onClose: () => void;
  words: Array<Word>;
  wordsInRepetitionProp: Array<WordInRepetition>;
  username: string;
}

const modalId = 'word-modal';

const WordPopup = ({ open, onClose, words, wordsInRepetitionProp, username }: WordPopupProps) => {

  const [wordsInRepetition, setWordsInRepetition] = useState<Array<WordInRepetition>>([]);
  useEffect(() => {
    if (open) {
      // @ts-ignore
      toggleModal(modalId, true);
    }
  }, [open]);

  useEffect(() => {
    setWordsInRepetition(wordsInRepetitionProp);
  }, [wordsInRepetitionProp]);

  const onPopupClose = () => {
    // @ts-ignore
    toggleModal(modalId, false);
    onClose();
  }

  const addRepetition = async (wordId: string) => {
    try {
      const repetition = await RepetitionsService.addRepetition(wordId, username);
      const repetitionId = repetition!.repetitionId!;
      setWordsInRepetition([...wordsInRepetition, { wordId: wordId, repetitionId }]);
    } catch (e) {
      console.error(e);
    }
  }

  const deleteRepetiton = async (wordId: string) => {
    try {
      const repetitionId = wordsInRepetition.find(w => w.wordId === wordId)?.repetitionId!;
      await RepetitionsService.deleteRepetiton(repetitionId);
      setWordsInRepetition(wordsInRepetition.filter(w => w.wordId !== wordId));
    } catch (e) {
      console.log(e);
    }
  }

  if (words.length === 0) {
    return null;
  }

  const prepareWordsView = () => {
    return <>
      <ol className="list-decimal dark:text-gray-400">
        {words.map(word => (
          <li key={word.wordId}>
            <span className="font-bold">{word.definition}</span>
            {
              wordsInRepetition.find(w => w.wordId === word.wordId) ?
                <button onClick={() => deleteRepetiton(word.wordId)} type="button" title="Usuń z powtórek" className="ml-2 text-xs font-medium text-white bg-red-700 hover:bg-red-800 focus:ring-4 focus:ring-red-300 font-medium rounded-full text-sm px-3 py-1.5 text-center dark:bg-red-600 dark:hover:bg-red-700 dark:focus:ring-red-900">-</button> :
                <button onClick={() => addRepetition(word.wordId)} type="button" title="Dodaj do powtórek" className="ml-2 text-xs font-medium text-white bg-green-700 hover:bg-green-800 focus:ring-4 focus:ring-green-300 font-medium rounded-full text-sm px-3 py-1.5 text-center dark:bg-green-600 dark:hover:bg-green-700 dark:focus:ring-green-800">+</button>
            }
            <ol className={"list-disc dark:text-gray-400 pl-6"}>
              {word.examples.map(example => (<li key={example.sentence}>{example.sentence}</li>))}
            </ol>
          </li>
        ))}
      </ol>
    </>;
  };

  return <>
    <div className="hidden overflow-y-auto overflow-x-hidden fixed right-0 left-0 top-4 z-50 justify-center items-center md:inset-0 h-modal sm:h-full" id={modalId}>
      <div className="relative px-4 w-full max-w-4xl h-full md:h-auto">
          <div className="relative bg-white rounded-lg shadow dark:bg-gray-700">
              <div className="flex justify-between items-center p-5 rounded-t border-b dark:border-gray-600">
                  <h3 className="text-xl font-medium text-gray-900 dark:text-white">
                      Definicje dla słowa: {words[0].entry}
                  </h3>
                  <button type="button" className="text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm p-1.5 ml-auto inline-flex items-center dark:hover:bg-gray-600 dark:hover:text-white" onClick={onPopupClose}>
                      <svg className="w-5 h-5" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fillRule="evenodd" d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z" clipRule="evenodd"></path></svg>  
                  </button>
              </div>
              <div className="p-6 space-y-6 pl-12 pr-12">
                {prepareWordsView()}
              </div>
          </div>
      </div>
  </div>
  </>

}

const mapStateToProps = (state: RootState) => {
  return {
    username: state.auth.user?.username!
  }
}

export default connect(mapStateToProps)(WordPopup);
