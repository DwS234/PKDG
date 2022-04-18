import { useEffect, useState } from "react";
import Repetition from "../../model/Repetition";
import RepetitionWithWord from "../../model/RepetitionWithWord";
import RepetitionsService from "../../services/repetitions/RepetitionsService";
import moment from "moment";

const DueRepetitions = () => {
	const [dueRepetitions, setDueRepetitons] = useState<Array<RepetitionWithWord>>([]);
	const [shouldShowAnswer, setShouldShowAnswer] = useState<boolean>(false);
	const [userText, setUserText] = useState<string>("");

	const getDefinitionClassName = (definition: string) => {
		let className = "mb-3 font-bold text-left";

		if (definition.toLowerCase() === userText.toLowerCase()) {
			className += " text-green-600";
		} else {
			className += " text-red-600";
		}

		return className;
	};

	const onShowAnswerClick = () => {
		setShouldShowAnswer(true);
	};

	const onRepetitionEvaluate = async (performanceRating: number, repetition: Repetition) => {
		const newEasiness = calculateNewEasiness(performanceRating, repetition.easiness);
		repetition.timesSeen += 1;
		repetition.easiness = newEasiness;

		scheduleRepetition(performanceRating, repetition);
		try {
			await RepetitionsService.updateRepetition(repetition.repetitionId!, repetition);
			if (dueRepetitions.length == 1) {
				setDueRepetitons([]);
			} else {
				setDueRepetitons(dueRepetitions.slice(1));
			}
			setShouldShowAnswer(false);
			setUserText("");
		} catch (e) {
			console.log(e);
		}
	};

	const calculateNewEasiness = (performanceRating: number, currentEasiness: number) => {
		let newEasiness = currentEasiness + (0.1 - (5 - performanceRating) * (0.08 + (5 - performanceRating) * 0.02));
		if (newEasiness < 1.3) {
			newEasiness = 1.3;
		} else if (newEasiness > 2.5) {
			newEasiness = 2.5;
		}
		return newEasiness;
	};

	const scheduleRepetition = (performanceRating: number, repetition: Repetition) => {
		const easiness = repetition.easiness;
		const consecutiveCorrectAnswers = repetition.consecutiveCorrectAnswers;

		if (performanceRating < 3) {
			repetition.consecutiveCorrectAnswers = 0;
			repetition.lastIntervalDays = 1;
			repetition.nextDate = moment().add(1, "day").toDate();
		} else {
			if (consecutiveCorrectAnswers == 0) {
				repetition.consecutiveCorrectAnswers = 1;
				repetition.lastIntervalDays = 1;
				repetition.nextDate = moment().add(1, "day").toDate();
			} else if (consecutiveCorrectAnswers == 1) {
				repetition.consecutiveCorrectAnswers = 2;
				repetition.lastIntervalDays = 6;
				repetition.nextDate = moment().add(6, "days").toDate();
			} else {
				const lastIntervalDays = repetition.lastIntervalDays;
				const newInterval = Math.floor(lastIntervalDays * easiness);
				repetition.nextDate = moment().add(newInterval, "day").toDate();
				repetition.lastIntervalDays = newInterval;
				repetition.consecutiveCorrectAnswers = consecutiveCorrectAnswers + 1;
			}
		}
	};

	useEffect(() => {
		(async function () {
			try {
				const repetitions = await RepetitionsService.getDueRepetitions();
				setDueRepetitons(repetitions);
			} catch (e) {
				console.error(e);
			}
		})();
	}, []);

	if (dueRepetitions.length == 0) {
		return <div className="mt-5 flex justify-center">Brak powtórek na ten moment.</div>;
	}

	return (
		<div className="mt-5 p-5 flex flex-col items-center">
			<h4>Pozostało: {dueRepetitions.length}</h4>
			<div className="flex flex-col items-center w-full mb-3 bg-white rounded-lg border shadow-md md:flex-row md:max-w-xl hover:bg-gray-100 dark:border-gray-700 dark:bg-gray-800 dark:hover:bg-gray-700">
				<div className="flex flex-col justify-between p-4 leading-normal">
					<h5 className="mb-3 font-bold text-gray-700 dark:text-gray-400 text-left">{dueRepetitions[0].word.entry}</h5>
				</div>
			</div>
			<div className="flex flex-col w-full items-center bg-white rounded-lg border shadow-md md:flex-row md:max-w-xl hover:bg-gray-100 dark:border-gray-700 dark:bg-gray-800 dark:hover:bg-gray-700">
				<div className="flex flex-col p-4 w-full leading-normal">
					{shouldShowAnswer && (
						<>
							<h5 className={getDefinitionClassName(dueRepetitions[0].word.definition)}>
								{dueRepetitions[0].word.definition}
							</h5>
							<ul className="list-disc pl-6">
								{dueRepetitions[0].word.examples.map((example) => (
									<li key={example.sentence}>{example.sentence}</li>
								))}
							</ul>
							<div className="flex flex-row mt-5 justify-center">
								<button
									onClick={() => onRepetitionEvaluate(0, dueRepetitions[0].repetition)}
									className="focus:outline-none text-white bg-red-700 hover:bg-red-800 focus:ring-4 focus:ring-red-300 font-medium rounded-lg text-sm px-5 py-2.5 mr-2 mb-2 dark:bg-red-600 dark:hover:bg-red-700 dark:focus:ring-red-900"
								>
									1
								</button>
								<button
									onClick={() => onRepetitionEvaluate(1, dueRepetitions[0].repetition)}
									className="focus:outline-none text-white bg-yellow-400 hover:bg-yellow-500 focus:ring-4 focus:ring-yellow-300 font-medium rounded-lg text-sm px-5 py-2.5 mr-2 mb-2 dark:focus:ring-yellow-900"
								>
									2
								</button>
								<button
									onClick={() => onRepetitionEvaluate(2, dueRepetitions[0].repetition)}
									className="py-2.5 px-5 mr-2 mb-2 text-sm font-medium text-gray-900 focus:outline-none bg-white rounded-lg border border-gray-200 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-4 focus:ring-gray-200 dark:focus:ring-gray-700 dark:bg-gray-800 dark:text-gray-400 dark:border-gray-600 dark:hover:text-white dark:hover:bg-gray-700"
								>
									3
								</button>
								<button
									onClick={() => onRepetitionEvaluate(3, dueRepetitions[0].repetition)}
									className="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 mr-2 mb-2 dark:bg-blue-600 dark:hover:bg-blue-700 focus:outline-none dark:focus:ring-blue-800"
								>
									4
								</button>
								<button
									onClick={() => onRepetitionEvaluate(4, dueRepetitions[0].repetition)}
									className="focus:outline-none text-white bg-purple-700 hover:bg-purple-800 focus:ring-4 focus:ring-purple-300 font-medium rounded-lg text-sm px-5 py-2.5 mb-2 dark:bg-purple-600 dark:hover:bg-purple-700 dark:focus:ring-purple-900"
								>
									5
								</button>
								<button
									onClick={() => onRepetitionEvaluate(5, dueRepetitions[0].repetition)}
									className="focus:outline-none ml-2 text-white bg-green-700 hover:bg-green-800 focus:ring-4 focus:ring-green-300 font-medium rounded-lg text-sm px-5 py-2.5 mr-2 mb-2 dark:bg-green-600 dark:hover:bg-green-700 dark:focus:ring-green-800"
								>
									6
								</button>
							</div>
						</>
					)}
					{!shouldShowAnswer && (
						<>
							<input type="text" value={userText} onChange={(e) => setUserText(e.target.value)} />
							<button
								onClick={onShowAnswerClick}
								className="cursor-pointer mt-2 text-white bg-blue-700 w-40 hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
							>
								Pokaż odpowiedź
							</button>
						</>
					)}
				</div>
			</div>
		</div>
	);
};

export default DueRepetitions;
