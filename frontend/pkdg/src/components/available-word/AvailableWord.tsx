import { useState } from "react";
import { ReactComponent as ExpandIcon } from "../../assets/svg/ExpandIcon.svg";

import Word from "../../model/Word";

interface AvailableWordProps {
	word: Word;
	onAddHandler: (wordId: string) => void;
}

const AvailableWord = ({ word, onAddHandler }: AvailableWordProps) => {
	const [showExamples, setShowExamples] = useState(false);

	return (
		<div className="relative border-b border-black last:border-b-0 mb-2 pb-2 last:pb-0">
			<h4 className="font-bold inline accordion-collapse-heading-1">{word.entry}</h4>
			<span> = {word.definition}</span>
			<button
				onClick={() => onAddHandler(word.wordId)}
				className="ml-2 text-xs font-medium text-white bg-green-700 hover:bg-green-800 focus:ring-4 focus:ring-green-300 font-medium rounded-full text-sm px-3 py-1.5 text-center dark:bg-green-600 dark:hover:bg-green-700 dark:focus:ring-green-800"
			>
				+
			</button>
			<ExpandIcon
				className={`absolute top-0 right-0 ${showExamples && "rotate-180"}`}
				onClick={() => setShowExamples((current) => !current)}
			/>
			{showExamples && (
				<ol className={"list-disc dark:text-gray-400 pl-6"}>
					{word.examples.map((example) => (
						<li key={example.sentence}>{example.sentence}</li>
					))}
				</ol>
			)}
		</div>
	);
};

export default AvailableWord;
