
package com.mb.ext.core.service.spec.content;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class SceneDTOList extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;

	private List<SceneDTO> scenes = new ArrayList<SceneDTO>();

	public List<SceneDTO> getScenes() {
		return scenes;
	}

	public void setScenes(List<SceneDTO> scenes) {
		this.scenes = scenes;
	}

}
