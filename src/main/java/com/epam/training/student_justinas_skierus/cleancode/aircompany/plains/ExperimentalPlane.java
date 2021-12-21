package com.epam.training.student_justinas_skierus.cleancode.aircompany.plains;

import com.epam.training.student_justinas_skierus.cleancode.aircompany.models.ExperimentalType;
import com.epam.training.student_justinas_skierus.cleancode.aircompany.security.ClassificationLevel;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
public class ExperimentalPlane extends Plane {

	private final @NonNull ExperimentalType type;
	private @NonNull ClassificationLevel classificationLevel;

	public ExperimentalPlane(String model, Integer maxSpeed,
			Integer maxFlightDistance, Integer maxLoadCapacity,
			ExperimentalType type, ClassificationLevel classificationLevel) {
		super(model, maxSpeed, maxFlightDistance, maxLoadCapacity);
		
		this.type = type;
		this.classificationLevel = classificationLevel;
	}
}
