let fixWorkWeek = document.getElementById("scheduleType1");
let shiftWorkSchedule = document.getElementById("scheduleType2");

let blocks = {
	fww: document.getElementById("fixed-work-week-block"),
	sws: document.getElementById("shift-work-schedule-block")
}

if (fixWorkWeek.checked) {
		setVisible(blocks.fww, blocks.sws)
	} else {
		setVisible(blocks.sws, blocks.fww);
	}

function setVisible(active, disabled) {
	active.style.display = "flex";
	disabled.style.display = "none";
}


fixWorkWeek.addEventListener("click", () => {
	console.log(blocks.fww);
	if (fixWorkWeek.checked) {
		setVisible(blocks.fww, blocks.sws)
	} else {
		setVisible(blocks.sws, blocks.fww);
	}
})

shiftWorkSchedule.addEventListener("click", () => {
		console.log("sws");

	if (shiftWorkSchedule.checked) {
		setVisible(blocks.sws, blocks.fww);
	} else {
		setVisible(blocks.fww, blocks.sws);
	}
})