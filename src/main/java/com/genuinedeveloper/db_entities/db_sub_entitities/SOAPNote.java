package com.genuinedeveloper.db_entities.db_sub_entitities;

import java.util.Map;

public class SOAPNote {
	
	private Subjective subjective;
	
	private Objective objective;
	
	private Assessment assessment;
	
	private Plan plan;

	public Subjective getSubjective() {
		return subjective;
	}

	public void setSubjective(Subjective subjective) {
		this.subjective = subjective;
	}

	public Objective getObjective() {
		return objective;
	}

	public void setObjective(Objective objective) {
		this.objective = objective;
	}

	public Assessment getAssessment() {
		return assessment;
	}

	public void setAssessment(Assessment assessment) {
		this.assessment = assessment;
	}

	public Plan getPlan() {
		return plan;
	}

	public void setPlan(Plan plan) {
		this.plan = plan;
	}
}
