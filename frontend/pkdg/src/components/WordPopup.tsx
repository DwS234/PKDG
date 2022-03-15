import { useEffect } from "react";
import Word from "../model/Word";

interface WordPopupProps {
  open: boolean;
  onClose: () => void;
  words: Array<Word>;
}

const modalId = 'word-modal';

const WordPopup = ({ open, onClose, words }: WordPopupProps) => {

  useEffect(() => {
    if (open) {
      // @ts-ignore
      toggleModal(modalId, true);
    }
  }, [open]);

  const onPopupClose = () => {
    // @ts-ignore
    toggleModal(modalId, false);
    onClose();
  }

  if (words.length === 0) {
    return null;
  }

  const prepareWordsView = () => {
    return <>
      <ol className="list-decimal dark:text-gray-400">
        {words.map(word => (
          <li>
            <span className="font-bold">{word.definition}</span>
            <ol className={"list-disc dark:text-gray-400 pl-6"}>
              {word.examples.map(example => (<li>{example.sentence}</li>))}
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
                      <svg className="w-5 h-5" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z" clipRule="evenodd"></path></svg>  
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

export default WordPopup;