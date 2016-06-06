package fr.exia.graphes.exo2;

import fr.exia.graphes.GraphUtils.Vertex2d;

public class Tache extends Vertex2d {

	/**
	 * La durée de la tâche en jours
	 */
	private double duration;
	
	private double earlyStart = -1;
	private double earlyFinish = -1;
	private double lateStart = -1;
	private double lateFinish = -1;

	public Tache(String name, double duration, double x, double y) {
		super(name, x, y);
		this.duration = duration;
	}

	public double getDuration() {
		return duration;
	}
	
	public void setDates(double start, double end) {
		if (earlyStart == -1) {
			earlyStart = lateStart = start;
			earlyFinish = lateFinish = end;
		}
		else {
			// Start
			if (start < earlyStart) {
				lateStart = Math.max(lateStart, earlyStart);
				earlyStart = start;
			}
			else {
				lateStart = Math.max(lateStart, start);
			}
			// Finish
			if (end < earlyFinish) {
				lateFinish = Math.max(lateFinish, earlyFinish);
				earlyFinish = end;
			}
			else {
				lateFinish = Math.max(lateFinish, end);
			}
		}
	}

	public double getEarlyStart() {
		return earlyStart;
	}

	public double getEarlyFinish() {
		return earlyFinish;
	}

	public double getLateStart() {
		return lateStart;
	}

	public double getLateFinish() {
		return lateFinish;
	}
	
	@Override
	public String toString() {
		return getName() + " (" + duration + ")";
	}

	public void setFloats(double d) {
		// TODO Auto-generated method stub
		
	}
	
}
